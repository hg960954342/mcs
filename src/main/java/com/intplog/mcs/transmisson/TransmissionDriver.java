package com.intplog.mcs.transmisson;

import com.intplog.mcs.bean.model.McsModel.McsLog;
import com.intplog.mcs.bean.model.McsModel.McsPlcConnect;
import com.intplog.mcs.common.PlcGetByte;
import com.intplog.mcs.common.PlcTypeUtils;
import com.intplog.mcs.common.SpringContextUtil;
import com.intplog.mcs.enums.McsLogType;
import com.intplog.mcs.service.McsService.McsLogService;
import com.intplog.mcs.transmisson.bean.BoilerData;
import com.intplog.mcs.transmisson.bean.BoilerTask;
import com.intplog.mcs.utils.StringUtil;
import com.intplog.siemens.SiemensNet;
import com.intplog.siemens.bean.ResultData;
import com.intplog.siemens.enumerate.SiemensPLCS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 11:33
 */
@Component
@Slf4j
public class TransmissionDriver implements Runnable {

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private McsLogService mcsLogService;

    /**
     * 连接状态
     */
    private boolean connected;

    public List<McsPlcConnect> plcConnectList = new ArrayList<>();
    public Map<String, String> plcPoint = new HashMap<>();
    private Map<String, SiemensNet> plcMap = new ConcurrentHashMap<>();
    private Map<String, SiemensNet> plcRead = new ConcurrentHashMap<>();
    public Map<String, BoilerData> boilerDataMap = new ConcurrentHashMap<>();
    public Map<String, Integer> controlMap = new ConcurrentHashMap<>();

    /**
     * 桶翻倒完成信号
     */
    public Queue<BoilerTask> boxQueue = new ConcurrentLinkedQueue<>();

    //region 构造方法
    public TransmissionDriver() {
    }


    public TransmissionDriver(List<McsPlcConnect> list) {
        plcConnectList = list;
        list.forEach(a -> {
            SiemensPLCS type = PlcTypeUtils.getPlcType(a.getType());
            SiemensNet siemensNet = new SiemensNet(type);
            plcPoint.put(a.getPlcName(), a.getIp());
            plcMap.put(a.getIp(), siemensNet);
            SiemensNet siemensNet1 = new SiemensNet(type);
            plcRead.put(a.getIp(), siemensNet1);

        });
    }
    //endregion

    /**
     * 是否连接
     *
     * @return
     */
    public boolean isConnected() {
        boolean connect = true;
        for (SiemensNet net : plcMap.values()) {
            connect = net.isConnectd();
            if (!connect) {
                return connect;
            }
        }

        for (SiemensNet net : plcRead.values()) {
            connect = net.isConnectd();
            if (!connect) {
                return connect;
            }
        }
        return connect;
    }

    @Override
    public void run() {
        Date begin;
        long temp = 0;
        init();

        while (true) {
            try {
                if (!isConnected()) {
                    connected();
                }
//                refreshData();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 初始化
     *
     * @param
     * @return void
     * @date 2020/9/4 14:22
     * @author szh
     */
    private void init() {
        mcsLogService = SpringContextUtil.getBean(McsLogService.class);
        createRefresh();
    }

    private void createRefresh() {
        for (McsPlcConnect  mcsPlcConnect : plcConnectList) {
            String ip=mcsPlcConnect.getIp();
            SiemensNet siemensNet = this.plcRead.get(ip);
            if (siemensNet == null) {
                continue;
            }
            int type = mcsPlcConnect.getFunction();
            if (type == 1) {
                Thread thread = new Thread(() -> {
                    String address = "DB6.DBB0";
                    int length = 10;
                    while (true) {
                        try {
                            if (siemensNet.isConnectd()) {
//                                ResultData resultData = readBytes(address, length,ip);
                                ResultData resultData = siemensNet.readBytes(address, length);
                                if (!resultData.isSuccess() || resultData.getValue() == null) {
                                    log.info(mcsPlcConnect.getPlcName() + "读取反馈异常:" + resultData.getMessage());
                                    continue;
                                }
                                byte[] bytes = (byte[]) resultData.getValue();
//                            log.info(Arrays.toString(bytes));
                                analyseData(bytes);
                            }
                            Thread.sleep(1000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            log.error(mcsPlcConnect.getPlcName() + "refresh error:" + ex.getMessage());
                        }
                    }
                });
                cachedThreadPool.execute(thread);
            } else if (type == 2) {
                Thread thread = new Thread(() -> {
                    while (true) {
                        try {
                            if (siemensNet.isConnectd()) {
//                                ResultData resultData1 = readBytes("DB2.DBB16", 96,ip);
//                                ResultData resultData2 = readBytes("DB2.DBB112", 96,ip);
                                ResultData resultData1 = siemensNet.readBytes("DB2.DBB16", 96);
                                ResultData resultData2 = siemensNet.readBytes("DB2.DBB112", 96);
                                if (!resultData1.isSuccess() || resultData2.getValue() == null || !resultData2.isSuccess() || resultData2.getValue() == null) {
                                    log.info(mcsPlcConnect.getPlcName() + "读取反馈异常:" + resultData1.getMessage() + resultData2.getMessage());
                                    continue;
                                }
                                byte[] bytes1 = (byte[]) resultData1.getValue();
                                byte[] bytes2 = (byte[]) resultData2.getValue();
//                            log.info(Arrays.toString(bytes));
                                analyseData(mcsPlcConnect.getPlcName(), bytes1, bytes2);
                            }
                            Thread.sleep(500);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            log.error(mcsPlcConnect.getPlcName() + "refresh error:" + ex.getMessage());
                        }
                    }
                });
                cachedThreadPool.execute(thread);
            } else {
                return;
            }
        }
    }

    private void analyseData(byte[] bytes) {
        int sign1 = bytes[0];
        int sign2 = bytes[1];
        int sign3 = bytes[2];
        int sign4 = bytes[3];
        int sign5 = bytes[4];
        int sign6 = bytes[5];
        int sign7 = bytes[6];
        int sign8 = bytes[7];
        int sign9 = bytes[8];
        int sign10 = bytes[9];

        controlMap.put("GR04", sign1);
        controlMap.put("GR0103", sign2);
        controlMap.put("GR0203", sign3);
        controlMap.put("GR0303", sign4);
        controlMap.put("GR0101", sign5);
        controlMap.put("GR0102", sign6);
        controlMap.put("GR0201", sign7);
        controlMap.put("GR0202", sign8);
        controlMap.put("em01", sign9);
        controlMap.put("em02", sign10);

    }

    private void analyseData(String area, byte[] bytes1, byte[] bytes2) {

        for (int i = 0; i < bytes1.length / 8; i++) {
            String group = area + String.format("%02d", i + 1);
            int arrive = PlcGetByte.bytes4ToIntHighEndian(bytes1, i * 8);
            int compelete = PlcGetByte.bytes4ToIntHighEndian(bytes1, i * 8 + 4);
            BoilerData boilerData = new BoilerData(group, arrive, compelete);
            comparePot(boilerData);
            boilerDataMap.put(group, boilerData);
        }

        for (int i = 0; i < bytes2.length / 8; i++) {
            String group = area + String.format("%02d", i + 13);
            int arrive = PlcGetByte.bytes4ToIntHighEndian(bytes2, i * 8);
            int compelete = PlcGetByte.bytes4ToIntHighEndian(bytes2, i * 8 + 4);
            BoilerData boilerData = new BoilerData(group, arrive, compelete);
            comparePot(boilerData);
            boilerDataMap.put(group, boilerData);
        }


    }

    private void comparePot(BoilerData tar) {
        BoilerData ori = boilerDataMap.get(tar.getGroup());
        if (ori == null) {
            return;
        }
        if (tar.getCompelete() != 0 && tar.getCompelete() != ori.getCompelete()) {
            BoilerTask boxTurnOver = new BoilerTask(tar.getGroup(), tar.getCompelete());
            this.boxQueue.offer(boxTurnOver);
        }
    }

    //region PLC操作

    /**
     * 连接PLC
     */
    private void connected() {
        if (plcMap == null || plcMap.size() == 0 || plcRead == null || plcRead.size() == 0) {
            return;
        }
        if (isConnected()) {
            return;
        }
        try {
            for (String ip : plcMap.keySet()) {
                SiemensNet siemensNet = plcMap.get(ip);
                if (!siemensNet.isConnectd()) {
                    siemensNet.connect(ip);
                    if (siemensNet.isConnectd()) {
                        String content = "PLC write:" + ip + "   连接成功";
                        log.info(content);
                        addMcsLog(content, McsLogType.PLCCONNECT.getDesc());
                    } else {
                        String content = "PLC write:" + ip + "   连接失败";
                        log.error(content);
                        addMcsLog(content, McsLogType.PLCCONNECT.getDesc());
                    }
                }
            }

            for (String ip : plcRead.keySet()) {
                SiemensNet siemensNet = plcRead.get(ip);
                if (!siemensNet.isConnectd()) {
                    siemensNet.connect(ip);

                    if (siemensNet.isConnectd()) {
                        String content = "PLC read:" + ip + "   连接成功";
                        log.info(content);
                        addMcsLog(content, McsLogType.PLCCONNECT.getDesc());
                    } else {
                        String content = "PLC read:" + ip + "   连接失败";
                        log.error(content);
                        addMcsLog(content, McsLogType.PLCCONNECT.getDesc());
                    }

                }
            }
        } catch (Exception ex) {
            connected = false;
            log.error("connect:" + ex.getMessage());
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        cachedThreadPool.shutdownNow();
        if (plcMap == null || plcMap.size() == 0) {
            return;
        }
        plcMap.values().forEach(a -> a.close());
        connected = false;
    }

    //region PLC读写操作

    /**
     * 读取PLC byte数组信息
     *
     * @param address
     * @param length
     * @return
     */
    public synchronized ResultData readBytes(String address, int length, String ip) {
        SiemensNet siemensNet = this.plcRead.get(ip);
        ResultData resultData2 = siemensNet.readBytes(address, length);
        return resultData2;
//        return plcRead.get(ip).readBytes(address, length);
    }

    /**
     * 读byte
     *
     * @param address
     * @return
     */
    public synchronized ResultData read(String address, String ip) {
        return plcRead.get(ip).read(address);
    }

    /**
     * 写入byte数组
     *
     * @param address
     * @param bytes
     * @return
     */
    public ResultData write(String address, byte[] bytes, String ip) {
//        long startTime = System.currentTimeMillis();
//        ResultData write = writeSiemensNet.write(address, bytes);
//        long endTime = System.currentTimeMillis();
//        log.info("TransmissionDriver写入PLC byte数组耗时:" + (endTime - startTime));
//        return write;

        return plcMap.get(ip).write(address, bytes);
    }

    /**
     * 写入byte数值
     *
     * @param address
     * @param bytes
     * @return
     */
    public synchronized ResultData write(String address, byte bytes, String ip) {
        return plcMap.get(ip).write(address, bytes);
    }

    /**
     * 写入int
     *
     * @param address
     * @param number
     * @return
     */
    public synchronized ResultData write(String address, int number, String ip) {
        return plcMap.get(ip).write(address, number);
    }

    //endregion

    //endregion

    //region 日志信息

    /**
     * 显示日志信息
     *
     * @param msg
     */
    public static void showLog(String msg) {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss SSSS");
        log.error(f.format(now) + "   " + msg);
    }

    /**
     * 添加MCS日志
     *
     * @param content
     * @param type
     */
    private void addMcsLog(String content, String type) {
        McsLog mcsLog = new McsLog();
        mcsLog.setContent(content);
        mcsLog.setType(type);
        mcsLog.setId(StringUtil.getUUID32());
        mcsLog.setCreateTime(new Date());
        mcsLogService.insertMcsLog(mcsLog);
    }

    //endregion
}
