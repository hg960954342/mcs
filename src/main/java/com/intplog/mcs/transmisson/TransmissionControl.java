package com.intplog.mcs.transmisson;

import com.alibaba.fastjson.JSON;
import com.intplog.mcs.bean.model.McsModel.*;
import com.intplog.mcs.bean.model.WmsModel.BoxTurnOver;
import com.intplog.mcs.bean.model.WmsModel.BucketDetectionDto;
import com.intplog.mcs.bean.model.WmsModel.EquipCommand;
import com.intplog.mcs.bean.model.WmsModel.WmsInterfacePrefix;
import com.intplog.mcs.common.*;
import com.intplog.mcs.enums.ScanType;
import com.intplog.mcs.rf.RfNet;
import com.intplog.mcs.service.InterfaceLogService;
import com.intplog.mcs.service.McsService.*;
import com.intplog.mcs.service.WmsService.WmsAccountService;
import com.intplog.mcs.transmisson.bean.BoilerTask;
import com.intplog.mcs.utils.StringUtil;
import com.intplog.mcs.weigh.WeighInit;
import com.intplog.mcs.weigh.bean.WeighData;
import com.intplog.siemens.SiemensNet;
import com.intplog.siemens.bean.ResultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 11:14
 */
@Component
@Scope("prototype")
@Slf4j
public class TransmissionControl implements Runnable {

    private WmsAccountService wmsAccountService;
    private InterfaceLogService interfaceLogService;
    private McsRfPropertiesService mcsRfPropertiesService;
    private McsWalkTaskService mcsWalkTaskService;
    private McsWalkRouteConfigService mcsWalkRouteConfigService;
    private McsPlcConnectService mcsPlcConnectService;
    private McsPlcVariableService mcsPlcVariableService;
    private McsRouteLogService mcsRouteLogService;

    /**
     * 输送线驱动层
     */
    public TransmissionDriver transmissionDriver;

    /**
     * 线程池
     */
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * RF采集器配置<设备编号,系统编号><0101,RF01>
     */
    private Map<String, McsRfProperties> rfConfig = new HashMap<>();

    /**
     * 路径配置
     */
    private List<McsWalkRouteConfig> routeConfigList = new ArrayList<>();

    /**
     * PLC命令下发点位<设备名称,DB地址>
     */
    private Map<String, McsPlcVariable> plcAddress = new HashMap<>();

    /**
     * 采集器上报数据队列
     */
    private Queue<RfData> rfData = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        Date begin;
        long temp = 0;
        init();

        while (true) {
            try {
                begin = new Date();

                //RF数据处理
                rfHandle();
//                temp = Utils.getTimeDifference(begin);
//                Thread.sleep(temp);
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("transmissionControl error:" + e.getMessage());
            }
        }
    }

    /**
     * RF数据处理
     *
     * @param
     * @return void
     * @date 2020/9/10 10:27
     * @author szh
     */
    private void rfHandle() {
        if (!RfNet.dataQue.isEmpty()) {
            //报文数据
            String poll = RfNet.dataQue.poll();
            if (poll == null) {
                return;
            }
            //解析RF数据
            RfData rfData = analyzeRf(poll);
            //开启线程对RF处理路径
            if (rfData != null) {
                cachedThreadPool.execute(new Thread(() -> walkLogic(rfData.getBcrId(), rfData.getContainer())));
            }
        }
    }

    /**
     * 解析RF数据
     *
     * @param
     * @return void
     * @date 2020/9/10 10:29
     * @author szh
     */
    private RfData analyzeRf(String msg) {
        //RFID-0101-123456789
        String[] split = msg.split("-");
        String equip = split[0];
        if (!equip.equals("RFID")) {
            return null;
        }
        if (!this.rfConfig.containsKey(split[1])) {
            return null;
        }

        return new RfData(split[1], split[2]);
    }

    /**
     * 路径规划逻辑
     *
     * @param connectId 设备编号
     * @param container 箱号
     * @return void
     * @date 2020/9/11 10:02
     * @author szh
     */
    private void walkLogic(String connectId, String container) {
        Date date = new Date();
        try {
            //RF采集器点位
            McsRfProperties point = this.rfConfig.get(connectId);
            //系统中未登记
            if (point == null) {
                log.error("RF采集器点位不存在:" + connectId);
                return;
            }
            //特殊RF业务处理
            rfSpecialProcess();
            //添加上报队列
            rfDataReport(point.getName(), container);
            //查询行走任务
            McsWalkTask task = mcsWalkTaskService.getByContainer(container);
            //路径信息
            if (!(task == null)) {
                if (task.getStatus().equals("1")) {
                    //查询对应路径
                    int route = getRoute(point, task);
                    //路径命令写入PLC
                    boolean result = writeWalkCommand(point, task, route);
                    //插入路径下发命令日志
                    mcsRouteLogService.insert(new McsRouteLog(point.getName(), route, container, new Date(), result));
                    //修改任务状态
                    List<McsWalkTarget> target = mcsWalkTaskService.getTarget(task.getTarget());
                    if (!(target == null)) {
                        for (int i = 0; i < target.size(); i++) {
                            if (target.get(i).getConnectId().equals(connectId)) {
                                int a = mcsWalkTaskService.updateMcsWalkTask1(task);
                                if (a > 0) {
                                    log.info("任务状态结束成功  ： " + task.getTaskId());
                                } else {
                                    log.info("任务状态结束失败  ： " + task.getTaskId());
                                }
                            } else continue;
                        }
                    } else if (target == null) {
                        log.info("检查修改任务状态的目的地配置   ： " + task.getTarget());
                    }
                } else {
                    log.info("walkLogic INFO: WMS未更新行走任务  " + connectId + "  " + container);
                }
            } else if (task == null) {
                log.info("walkLogic INFO: WMS未下发行走任务  " + connectId + "  " + container);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("walkLogic ERROR:" + e.getMessage());
        }
    }

    /**
     * 获取目的位置
     *
     * @param containerNo
     * @return java.lang.String
     * @date 2020/9/10 14:54
     * @author szh
     */
    private String getLocation(String containerNo) {
        String address;
        //根据箱号查询路径任务
        McsWalkTask task = mcsWalkTaskService.getByContainer(containerNo);
        if (task == null) {
            address = "ERO";
        } else {
            address = task.getTarget();
        }
        return address;
    }

    /**
     * 获取路径信息
     *
     * @param point
     * @param
     * @return int
     * @date 2020/9/10 17:13
     * @author szh
     */
    private int getRoute(McsRfProperties point, McsWalkTask task) {
        int route = 0;
        //采集器类型
        int type = point.getType();
        //采集器编号
        String bcrId = point.getName();
        String location;
        if (task == null) {
            location = "NULL";
        } else {
            location = task.getTarget();
            switch (ScanType.ofValue(type)) {
                case STR:
                    location = location.substring(0, 2);
                    break;
                case AREA:
                    location = location.substring(0, 4);
                    break;
                case NODE:
                    break;
            }
        }
        route = calculateRoute(bcrId, location);
        return route;
    }

    /**
     * 计算路径
     *
     * @param bcrId
     * @param location
     * @return int
     * @date 2020/9/10 17:13
     * @author szh
     */
    private int calculateRoute(String bcrId, String location) {
        int route = 0;
        List<McsWalkRouteConfig> collect = routeConfigList.stream()
                .filter(point -> (point.getPoint().equals(bcrId)))
                .filter(point -> point.getTarget().contains(location))
                .limit(1)
                .collect(Collectors.toList());
        if (collect.size() != 0) {
            route = collect.get(0).getRoute();
        } else {
            List<McsWalkRouteConfig> ero = routeConfigList.stream()
                    .filter(point -> (point.getPoint().equals(bcrId)))
                    .filter(point -> point.getTarget().contains("ERO"))
                    .limit(1)
                    .collect(Collectors.toList());
            if (ero.size() != 0) {
                route = ero.get(0).getRoute();
            } else {

            }
        }
        return route;
    }

    /**
     * 特殊RF业务处理
     *
     * @param
     * @return void
     * @date 2020/9/10 17:44
     * @author szh
     */
    private void rfSpecialProcess() {

    }

    /**
     * 添加上报队列
     *
     * @param bcrId
     * @param container
     * @return void
     * @date 2020/9/11 11:37
     * @author szh
     */
    private void rfDataReport(String bcrId, String container) {
        RfData rfData = new RfData(bcrId, container);
        this.rfData.offer(rfData);
    }

    /**
     * 行走命令写入PLC
     *
     * @param
     * @return void
     * @date 2020/9/10 17:48
     * @author szh
     */
    private boolean writeWalkCommand(McsRfProperties point, McsWalkTask task, int route) {
        boolean flag = false;
        McsPlcVariable mcsPlcVariable = this.plcAddress.get(point.getName());
        if (mcsPlcVariable == null) {
            log.error("行走命令写入失败,点位不存在:" + point.getName());
            return flag;
        }
        //获取写入地址
        String address = mcsPlcVariable.getAddress();
        if (address == null) {
            return flag;
        }
//        route = 2;
        ByteBuf byteBuf;
        int a = 0;
        if (point.getType() == 3) {
            int taskId = task == null ? Integer.parseInt(StringUtil.getTaskCode()) : task.getMcsId();
            a = taskId;
            byte[] bytes1 = PlcGetByte.intToBytes4HighEndian(taskId);
            byte[] bytes2 = {(byte) route};
            byteBuf = Unpooled.copiedBuffer(bytes1, bytes2);
        } else {
            byte[] bytes2 = {(byte) route};
            byteBuf = Unpooled.copiedBuffer(bytes2);
        }
        //写入的byte数组
        byte[] array = byteBuf.array();

        //写入PLC
        ResultData write = this.transmissionDriver.write(address, array, point.getRemark());
        flag = write.isSuccess();
        if (point.getType() == 3) {
            log.info(point.getName() + "写入路径:" + route + "写入任务:" + a + " 地址:" + address + " IP:" + point.getRemark() + " 结果:" + flag);
        } else {
            log.info(point.getName() + "写入路径:" + route + " 地址:" + address + " IP:" + point.getRemark() + " 结果:" + flag);
        }


        return flag;
    }

    public boolean writeEquipCommand(EquipCommand equipCommand) {
        boolean flag = false;
        McsPlcVariable mcsPlcVariable = plcAddress.get(equipCommand.getPoint());
        if (mcsPlcVariable == null) {
            return flag;
        }

        String ip = transmissionDriver.plcPoint.get(mcsPlcVariable.getPlcName());
        if (ip == null) {
            return flag;
        }
        byte command = 0;
        if (equipCommand.getPoint().equals("S01") || equipCommand.getPoint().equals("S02") || equipCommand.getPoint().equals("S03") || equipCommand.getPoint().equals("S04")) {
            command = (byte) (Byte.valueOf(equipCommand.getCommand()));
        } else {
            command = (byte) (Byte.valueOf(equipCommand.getCommand()) + 1);
        }

        ResultData resultData = transmissionDriver.write(mcsPlcVariable.getAddress(), command, ip);
        log.info(equipCommand.getPoint() + "写入路径:" + command + " 地址:" + mcsPlcVariable.getAddress() + " IP:" + ip + " 结果:" + resultData.isSuccess());
        return resultData.isSuccess();
    }

    /**
     * 读取静态称重量并上报WMS
     *
     * @param
     * @return void
     * @date 2021/5/25 17:48
     * @author hg
     */
    public void staticWeight() {
        try {
            for (int i = 1; i <= 7; i++) {
                String a = "WE0" + String.valueOf(i) + "_REA";
                String b = "WE0" + String.valueOf(i);
                McsPlcVariable mcsPlcVariable = plcAddress.get(a);
                McsPlcVariable mcsPlcVariable1 = plcAddress.get(b);
                String ip = transmissionDriver.plcPoint.get(mcsPlcVariable.getPlcName());
                String ip1 = transmissionDriver.plcPoint.get(mcsPlcVariable1.getPlcName());
                ResultData data = transmissionDriver.readBytes(mcsPlcVariable.getAddress(), 1, ip);
                byte[] value = (byte[]) data.getValue();
                if (value[0] == 1) {
                    ResultData data1 = transmissionDriver.readBytes(mcsPlcVariable1.getAddress(), 4, ip1);
                    byte[] value1 = (byte[]) data1.getValue();
                    float weight = PlcGetByte.byte2float(value1);
                    double weight1 = Double.valueOf(String.valueOf(weight));

                    WeighData weighData = new WeighData();
                    weighData.setNumber(b);
                    weighData.setType("1");
                    weighData.setWeight(weight1);
                    String s = JSON.toJSONString(weighData);
                    WmsInterfacePrefix data2 = new WmsInterfacePrefix("Weighing", s);
                    WmsJsonData wmsJsonData = weightReport(data2);
//                    if (wmsJsonData.getStateCode()==1) {
//                        log.info("静态称重量上报Wms成功: " + weighData);
//                    } else {
//                        log.info("静态称重量上报Wms失败: " + wmsJsonData.getMessage() + "  " + weighData);
//                    }
                } else continue;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    /**
     * 读取桶位状态并上报WMS
     *
     * @param
     * @return void
     * @date 2021/5/25 17:48
     * @author hg
     */
    public int bucketCheck(BucketDetectionDto bucketDetectionDto) {
        try {
            McsPlcVariable mcsPlcVariable = plcAddress.get(bucketDetectionDto.getTarget());
            String ip = transmissionDriver.plcPoint.get(mcsPlcVariable.getPlcName());
            ResultData data = transmissionDriver.readBytes(mcsPlcVariable.getAddress(), 1, ip);
            byte flag = 0;
            if (data.isSuccess()) {
                byte[] value = (byte[]) data.getValue();
                if (value[0] == 1) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            }
            return flag;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            int flag = 99;
            return flag;
        }

    }


    /**
     * 关闭连接
     */
    public void close() {
        cachedThreadPool.shutdownNow();
        transmissionDriver.close();
    }

    //region 初始化

    /**
     * 初始化
     *
     * @param
     * @return void
     * @date 2020/9/3 16:00
     * @author szh
     */
    private void init() {
        //加载容器
        initBean();
        //加载RF采集器配置
        addRfConfig();
        //加载路径配置
        addRouteConfig();
        addPointVariable();
        //创建驱动层
        createDri();
        //创建RF上报线程
        createRfReport();
        //创建重量处理线程
        createWeight();
        //创建桶翻倒完成上报
        createBoxTurnReport();
        //创建空桶释放上报
//        createEmptyBoxAsk();
    }

    /**
     * 加载容器
     *
     * @param
     * @return void
     * @date 2020/9/8 16:49
     * @author szh
     */
    private void initBean() {
        wmsAccountService = SpringContextUtil.getBean(WmsAccountService.class);
        interfaceLogService = SpringContextUtil.getBean(InterfaceLogService.class);
        mcsRfPropertiesService = SpringContextUtil.getBean(McsRfPropertiesService.class);
        mcsWalkTaskService = SpringContextUtil.getBean(McsWalkTaskService.class);
        mcsWalkRouteConfigService = SpringContextUtil.getBean(McsWalkRouteConfigService.class);
        mcsPlcConnectService = SpringContextUtil.getBean(McsPlcConnectService.class);
        mcsPlcVariableService = SpringContextUtil.getBean(McsPlcVariableService.class);
        mcsRouteLogService = SpringContextUtil.getBean(McsRouteLogService.class);
    }

    /**
     * 加载RF采集器配置
     *
     * @param
     * @return void
     * @date 2020/9/10 10:46
     * @author szh
     */
    private void addRfConfig() {
        //RF配置
        List<McsRfProperties> pointList = mcsRfPropertiesService.getAll();
        if (pointList.size() == 0) {
            return;
        }
        //加载配置
        pointList.stream().forEach(point -> rfConfig.put(point.getConnectId(), point));
    }

    /**
     * 加载路径配置
     *
     * @param
     * @return void
     * @date 2020/9/10 14:18
     * @author szh
     */
    private void addRouteConfig() {
        List<McsWalkRouteConfig> routeList = mcsWalkRouteConfigService.getAll();
        if (routeList.size() == 0) {
            return;
        }
        routeList.stream().forEach(routeConfig -> this.routeConfigList.add(routeConfig));
    }

    /**
     * @param
     * @return void
     * @date 2021/3/9 18:02
     * @author szh
     */
    private void addPointVariable() {
        List<McsPlcVariable> list = mcsPlcVariableService.getAll();
        if (list.size() == 0) {
            return;
        }

        list.forEach(a -> this.plcAddress.put(a.getGroupNumber(), a));
    }

    /**
     * 创建驱动层
     *
     * @param
     * @return void
     * @date 2020/9/4 14:38
     * @author szh
     */
    private void createDri() {
        //加载并遍历所有PLC
        List<McsPlcConnect> mcsPlcConnectList = mcsPlcConnectService.getAll();
        if (mcsPlcConnectList.size() == 0) {
            return;
        }
        transmissionDriver = new TransmissionDriver(mcsPlcConnectList);
        this.cachedThreadPool.execute(transmissionDriver);
    }

    //region RF线程

    /**
     * 创建RF处理线程
     *
     * @param
     * @return void
     * @date 2020/9/4 14:43
     * @author szh
     */
    private void createRfReport() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Date begin;
                long temp = 0;
                while (true) {
                    try {
                        begin = new Date();
                        //RF采集数据上报
                        rfReport();
                        temp = Utils.getTimeDifference(begin);
                        Thread.sleep(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("RF Report Error:" + e.getMessage());
                    }
                }
            }
        };

        cachedThreadPool.execute(thread);
    }

    /**
     * RF采集数据上报
     *
     * @param
     * @return void
     * @date 2020/9/9 17:18
     * @author szh
     */
    private void rfReport() {
        if (rfData.isEmpty()) {
            return;
        }
        RfData poll = rfData.poll();
        String s = JSON.toJSONString(poll);
        WmsInterfacePrefix data = new WmsInterfacePrefix("Collection_RFID", s);
        WmsJsonData wmsJsonData = rfReport(data);
        if (wmsJsonData.getStateCode() != 1) {
            rfData.offer(poll);
        }
    }
    //endregion

    //region 重量线程

    /**
     * 创建重量处理线程
     *
     * @param
     * @return void
     * @date 2020/9/4 14:48
     * @author szh
     */
    private void createWeight() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Date begin;
                long temp = 0;
                while (true) {
                    try {
                        begin = new Date();

                        //重量数据处理
                        weightHandle();

                        temp = Utils.getTimeDifference(begin);
                        Thread.sleep(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("Weigh Report Error:" + e.getMessage());
                    }
                }
            }
        };

        cachedThreadPool.execute(thread);
    }

    /**
     * 重量数据处理
     *
     * @param
     * @return void
     * @date 2020/9/8 16:45
     * @author szh
     */
    private void weightHandle() {
        if (!WeighInit.queue.isEmpty()) {
            WeighData poll = WeighInit.queue.poll();
            String s = JSON.toJSONString(poll);
            WmsInterfacePrefix data = new WmsInterfacePrefix("Weighing", s);
            //重量上报
            WmsJsonData wmsJsonData = weightReport(data);
//            if (wmsJsonData.getStateCode()==1) {
//                log.info("动态称重量上报Wms成功:" + s);
//            } else {
//                log.info("动态称重量上报Wms失败: " + wmsJsonData.getMessage() + "  " + s);
//            }
        }
    }
    //endregion

    //region 桶翻线程

    /**
     * 创建桶翻倒完成上报
     *
     * @param
     * @return void
     * @date 2020/9/10 9:21
     * @author szh
     */
    private void createBoxTurnReport() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Date begin;
                long temp = 0;
                while (true) {
                    try {
                        begin = new Date();

                        //桶翻倒完成数据处理
                        boxTurnHandle();

                        temp = Utils.getTimeDifference(begin);
                        Thread.sleep(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("Box Turn Report Error:" + e.getMessage());
                    }
                }
            }
        };

        cachedThreadPool.execute(thread);
    }

    /**
     * 桶翻倒完成数据处理
     *
     * @param
     * @return void
     * @date 2020/9/10 9:26
     * @author szh
     */
    private void boxTurnHandle() {
        if (transmissionDriver.boxQueue.isEmpty()) {
            return;
        }

        BoilerTask poll = transmissionDriver.boxQueue.poll();
        log.info(poll.toString());
        McsWalkTask task = mcsWalkTaskService.getByMcsId(poll.getMcsId());
        if (task == null) {
            return;
        }
        BoxTurnOver boxTurnOver = new BoxTurnOver(poll.getGroup(), task.getContainerNo());
        String s = JSON.toJSONString(boxTurnOver);
        WmsInterfacePrefix data = new WmsInterfacePrefix("OverturnFinish", s);
        log.info("桶翻倒完成上报Wms:" + data);
        //桶翻倒完成上报
        boxTurnReport(data);
    }
    //endregion

    //region 空桶申请线程

    /**
     * 创建空桶申请信号
     *
     * @param
     * @return void
     * @date 2020/9/10 9:47
     * @author szh
     */
    private void createEmptyBoxAsk() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Date begin;
                long temp = 0;
                while (true) {
                    try {
                        begin = new Date();

                        //空桶申请处理
                        emptyBoxHandle();

                        temp = Utils.getTimeDifference(begin);
                        Thread.sleep(1000 * 3);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("Box Turn Report Error:" + e.getMessage());
                    }
                }
            }
        };
        cachedThreadPool.execute(thread);
    }

    /**
     * 空桶申请处理
     *
     * @param
     * @return void
     * @date 2020/9/10 9:49
     * @author szh
     */
    private void emptyBoxHandle() {
        WmsInterfacePrefix data = new WmsInterfacePrefix("EmptyBarrelRelease", null);
        //空桶完成上报
        emptyBoxAsk(data);
    }
    //endregion

    //endregion

    //region 接口内容

    /**
     * 重量上报
     *
     * @param
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/8 17:39
     * @author szh
     */
    public WmsJsonData weightReport(Object object) {
        Date begin = new Date();
        WmsJsonData wmsJsonData = wmsAccountService.weighReport(object);
        Date end = new Date();
        //记录接口日志
        interfaceLogService.insertInterfaceLog("重量上报Wms", object, begin, wmsJsonData, end);
        return wmsJsonData;
    }

    /**
     * 静态称重量上报
     *
     * @param object
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/9 18:01
     * @author szh
     */
    public WmsJsonData staticWeightReport(Object object) {
        Date begin = new Date();
        WmsJsonData wmsJsonData = wmsAccountService.staticWeight(object);
        Date end = new Date();
        //记录接口日志
        interfaceLogService.insertInterfaceLog("桶翻倒完成上报Wms", object, begin, wmsJsonData, end);
        return wmsJsonData;
    }

    /**
     * RF数据上报
     *
     * @param object
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/9 17:57
     * @author szh
     */
    public WmsJsonData rfReport(Object object) {
        Date begin = new Date();
        WmsJsonData wmsJsonData = wmsAccountService.rfReport(object);
        Date end = new Date();
        //记录接口日志
        interfaceLogService.insertInterfaceLog("RF上报Wms", object, begin, wmsJsonData, end);
        return wmsJsonData;
    }

    /**
     * 桶翻倒完成上报
     *
     * @param object
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/9 18:01
     * @author szh
     */
    public WmsJsonData boxTurnReport(Object object) {
        Date begin = new Date();
        WmsJsonData wmsJsonData = wmsAccountService.boxTurnOver(object);
        Date end = new Date();
        //记录接口日志
        interfaceLogService.insertInterfaceLog("桶翻倒完成上报Wms", object, begin, wmsJsonData, end);
        return wmsJsonData;
    }


    /**
     * 空桶申请信号
     *
     * @param object
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/10 9:17
     * @author szh
     */
    public WmsJsonData emptyBoxAsk(Object object) {
        Date begin = new Date();
        WmsJsonData wmsJsonData = wmsAccountService.emptyBoxRelease(object);
        Date end = new Date();
        //记录接口日志
        interfaceLogService.insertInterfaceLog("空桶申请上报Wms", object, begin, wmsJsonData, end);
        return wmsJsonData;
    }
    //endregion
}
