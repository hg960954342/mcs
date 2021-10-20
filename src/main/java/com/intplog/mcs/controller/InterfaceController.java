package com.intplog.mcs.controller;

import com.intplog.mcs.bean.model.McsModel.McsPlcVariable;
import com.intplog.mcs.bean.model.WmsModel.BucketDetectionDataDto;
import com.intplog.mcs.bean.model.WmsModel.BucketDetectionDto;
import com.intplog.mcs.transmisson.TransmissionControl;
import com.intplog.mcs.transmisson.TransmissionServer;
import com.intplog.mcs.bean.model.LedDisplay;
import com.intplog.mcs.bean.model.McsModel.McsWalkTask;
import com.intplog.mcs.bean.model.WmsModel.EquipCommand;
import com.intplog.mcs.bean.model.WmsModel.LedTask;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.InterfaceLogService;
import com.intplog.mcs.service.LedDisplayService;
import com.intplog.mcs.service.McsService.McsWalkTaskService;
import com.intplog.siemens.bean.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-04 17:17
 **/
@Controller
@RequestMapping("/Interface")
@Slf4j
public class InterfaceController {

    @Autowired
    private InterfaceLogService interfaceLogService;
    @Autowired
    private McsWalkTaskService mcsWalkTaskService;
    @Autowired
    private LedDisplayService ledDisplayService;
    private Object BucketDetectionDataDto;

    //region Eis接口

    //    /**
    //     * EIS出入库任务下发
    //     *
    //     * @param eisRequestDto
    //     * @return
    //     */
    //    @PostMapping("/Request")
    //    @ResponseBody
    //    public Object addTaskByEis(@RequestBody @Validated EisBankTaskDto eisRequestDto) {
    //
    //        Date begin = new Date();
    //        JsonData jsonData = addTask(eisRequestDto.getCarryList());
    //        Date end = new Date();
    //        ///记录接口任务下发日志
    //        interfaceLogService.insertInterfaceLog("EIS出入库任务下发", eisRequestDto, begin, jsonData, end);
    //        return jsonData;
    //    }
    //
    //    /**
    //     * 处理任务
    //     *
    //     * @param eisRequests
    //     * @return
    //     */
    //    public JsonData addTask(ArrayList<EisRequest> eisRequests) {
    //        JsonData jsonData = new JsonData();
    //        String message = "操作成功";
    //        boolean isOk = false;
    //        String code = "500";
    //        ArrayList<EisErrorData> errorList = new ArrayList<>();
    //        if (eisRequests.size() > 0) {
    //            for (EisRequest eisRequest : eisRequests) {
    //                McsTaskInfo mcsTaskInfo = mcsTaskInfoService.getByTaskId(eisRequest.getTaskId());
    //                //异常任务重复下发
    //                if (eisRequest.getStatus() == 1) {
    //                    if (mcsTaskInfo == null) {
    //                        message += ",任务编号为:" + eisRequest.getTaskId() + "的任务,异常重复下发不存在";
    //                        continue;
    //                    }
    //                    if (eisRequest.getType() == TaskType.InTask.getValue()) {
    //                        mcsTaskInfo.setTarget(eisRequest.getTarget());
    //                        mcsTaskInfo.setSource(eisRequest.getAddress());
    //                        mcsTaskInfo.setPriority(eisRequest.getPriority());
    //                        mcsTaskInfo.setStatus(BankTaskStage.RESEND.getValue());
    //                        int update = mcsTaskInfoService.update(mcsTaskInfo);
    //                        if (update > 0) {
    //                            isOk = true;
    //                            code = "200";
    //                            message = "操作成功";
    //                        }
    //                    }
    //                }
    //                //正常下发
    //                else if (eisRequest.getStatus() == 0) {
    //                    if (mcsTaskInfo != null) {
    //                        message += ",任务编号为:" + eisRequest.getTaskId() + "的任务,已存在";
    //                        errorList.add(new EisErrorData(eisRequest.getTaskId(), "102"));
    //                        continue;
    //                    }
    ////                    McsTaskInfo byMcsId = mcsTaskInfoService.getByMcsId(eisRequest.getContainerNo());
    //                    int i = insertTaskInfo(eisRequest);
    //
    //                    if (i > 0) {
    //                        isOk = true;
    //                        code = "200";
    //                        message = "操作成功";
    //                    } else {
    //                        message += ",任务编号为:" + eisRequest.getTaskId() + "插入数据库失败";
    //                        errorList.add(new EisErrorData(eisRequest.getTaskId(), "102"));
    //                    }
    //                }
    //
    //            }
    //
    //        } else {
    //            message = "请求任务为空";
    //        }
    //        jsonData.setSuccess(isOk);
    //        jsonData.setCode(code);
    //        jsonData.setMessage(message);
    //        return jsonData;
    //    }
    //
    //    /**
    //     * 插入数据库
    //     *
    //     * @param eisRequest
    //     * @return
    //     */
    //    private int insertTaskInfo(EisRequest eisRequest) {
    //        McsTaskInfo mcsTaskInfo = new McsTaskInfo();
    //        mcsTaskInfo.setTaskId(eisRequest.getTaskId());
    //        //mcsTaskInfo.setMcsId(Integer.valueOf(eisRequest.getContainerNo()));
    //        mcsTaskInfo.setType(eisRequest.getType());
    //        mcsTaskInfo.setStockId(eisRequest.getContainerNo());
    //        mcsTaskInfo.setSource(eisRequest.getAddress());
    //        mcsTaskInfo.setTarget(eisRequest.getTarget());
    //        mcsTaskInfo.setWeight(eisRequest.getWeight());
    //        mcsTaskInfo.setPriority(eisRequest.getPriority());
    //        mcsTaskInfo.setCreateTime(new Date());
    //        mcsTaskInfo.setStatus(BankTaskStage.RECEIVE.getValue());
    //        return mcsTaskInfoService.insert(mcsTaskInfo);
    //    }
    //

    //endregion

    //region WMS接口

    //region WMS下发行走任务接口

    /**
     * WMS下发行走任务接口
     *
     * @param mcsWalkTask
     * @return java.lang.Object
     * @date 2020/9/11 12:15
     * @author szh
     */
    @PostMapping("/WalkTask")
    @ResponseBody
    public Object acceptWalkTask(@RequestBody @Validated McsWalkTask mcsWalkTask) {
        Date begin = new Date();
        JsonData jsonData = addWalkTask(mcsWalkTask);
        Date end = new Date();
        //记录接口任务下发日志
        interfaceLogService.insertInterFaceLogByResPCross("WMS行走任务下发", mcsWalkTask, begin, jsonData, end);
        return jsonData;
    }

    /**
     * 行走任务处理
     *
     * @param mcsWalkTask
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/11 12:15
     * @author szh
     */
    private JsonData addWalkTask(McsWalkTask mcsWalkTask) {
        JsonData jsonData = new JsonData();
        boolean isOk = false;
        String msg = "操作失败";
        String code = "500";
        try {
            //查询箱号任务
            McsWalkTask task = mcsWalkTaskService.getByContainer(mcsWalkTask.getContainerNo());
            int i;
            //不存在则插入
            if (task == null) {
                i = mcsWalkTaskService.insertMcsWalkTask(mcsWalkTask);
            }
            //存在则更新
            else {
                i = mcsWalkTaskService.updateMcsWalkTask(mcsWalkTask);
            }
            if (i > 0) {
                isOk = true;
                msg = "操作成功";
                code = "200";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        } finally {
            jsonData.setSuccess(isOk);
            jsonData.setMessage(msg);
            jsonData.setCode(code);
            return jsonData;
        }
    }
    //endregion

    //region WMS下发LED任务接口

    /**
     * WMS下发LED任务接口
     *
     * @param
     * @return java.lang.Object
     * @date 2020/9/11 14:03
     * @author szh
     */
    @PostMapping("/LedTask")
    @ResponseBody
    public Object ledTask(@RequestBody @Validated LedTask ledTask) {
        Date begin = new Date();
        JsonData jsonData = ledTaskHandle(ledTask);
        Date end = new Date();
        //记录接口任务下发日志
        interfaceLogService.insertInterFaceLogByResPCross("WMS LED任务下发", ledTask, begin, jsonData, end);
        return jsonData;
    }

    /**
     * LED数据处理
     *
     * @param ledTask
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/11 14:06
     * @author szh
     */
    private JsonData ledTaskHandle(LedTask ledTask) {
        JsonData jsonData = new JsonData();
        boolean isOk = false;
        String msg = "操作失败";
        String code = "500";
        try {
            //获取LED任务
            LedDisplay task = ledDisplayService.getLedDisplayById(ledTask.getTaskId());
            int i = 0;
            //不存在则添加
            if (task == null) {
                i = addLedTask(ledTask);
            }
            //存在则更新
            else {
                i = updateLedTask(ledTask);
            }

            if (i > 0) {
                isOk = true;
                msg = "操作成功";
                code = "200";
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        } finally {
            jsonData.setSuccess(isOk);
            jsonData.setMessage(msg);
            jsonData.setCode(code);
            return jsonData;
        }
    }

    /**
     * 添加LED数据
     *
     * @param ledTask
     * @return int
     * @date 2020/9/11 14:19
     * @author szh
     */
    private int addLedTask(LedTask ledTask) {
        LedDisplay ledDisplay = new LedDisplay();
        ledDisplay.setId(ledTask.getTaskId());
        ledDisplay.setContent(ledTask.getData());
        ledDisplay.setDisplayFormat(ledTask.getLedId());
        ledDisplay.setStatus(ledTask.getStatus());
        int i = ledDisplayService.insertLedDisplay(ledDisplay);
        return i;
    }

    /**
     * 更新LED数据
     *
     * @param ledTask
     * @return int
     * @date 2020/9/11 14:24
     * @author szh
     */
    private int updateLedTask(LedTask ledTask) {
        LedDisplay ledDisplay = new LedDisplay();
        ledDisplay.setId(ledTask.getTaskId());
        ledDisplay.setContent(ledTask.getData());
        ledDisplay.setDisplayFormat(ledTask.getLedId());
        ledDisplay.setStatus(ledTask.getStatus());
        int i = ledDisplayService.updateLedDisplay(ledDisplay);
        return i;
    }
    //endregion

    //region WMS下发设备指令接口

    /**
     * WMS下发设备指令接口
     *
     * @param equipCommand
     * @return java.lang.Object
     * @date 2020/9/11 16:07
     * @author szh
     */
    @PostMapping("/EquipCommand")
    @ResponseBody
    public Object acceptWalkTask(@RequestBody @Validated EquipCommand equipCommand) {
        Date begin = new Date();
        JsonData jsonData = equipTask(equipCommand);
        Date end = new Date();
        //记录接口任务下发日志
        interfaceLogService.insertInterFaceLogByResPCross("WMS滾揉任务下发", equipCommand, begin, jsonData, end);
        return jsonData;
    }

    /**
     * 执行设备指令
     *
     * @param equipCommand
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/11 15:35
     * @author szh
     */
    private JsonData equipTask(EquipCommand equipCommand) {
        JsonData jsonData = new JsonData();
        boolean isOk = false;
        String msg = "操作失败，WMS点位配置错误：" + equipCommand.getPoint();
        String code = "500";
        try {
            TransmissionControl plc1 = TransmissionServer.transmissionControl;
            if (plc1 != null) {
                //指令下发PLC
                boolean result = plc1.writeEquipCommand(equipCommand);
                if (result) {
                    isOk = true;
                    msg = "操作成功";
                    code = "200";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        } finally {
            jsonData.setSuccess(isOk);
            jsonData.setMessage(msg);
            jsonData.setCode(code);
            return jsonData;
        }
    }
    //endregion

    //region WMS下发桶位查询接口

    /**
     * WMS下发桶位查询接口
     *
     * @param bucketDetectionDto
     * @return java.lang.Object
     * @date 2020/9/11 16:07
     * @author szh
     */
    @PostMapping("/BucketDetection")
    @ResponseBody
    public Object bucketDetection(@RequestBody @Validated BucketDetectionDto bucketDetectionDto) {
        Date begin = new Date();
        JsonData jsonData = bucket(bucketDetectionDto);
        Date end = new Date();
        //记录接口任务下发日志
        interfaceLogService.insertInterFaceLogByResPCross("WMS下发桶位查询接口", bucketDetectionDto, begin, jsonData, end);
        return jsonData;
    }

    /**
     * 执行设备指令
     *
     * @param bucketDetectionDto
     * @return com.intplog.mcs.common.JsonData
     * @date 2020/9/11 15:35
     * @author szh
     */
    private JsonData bucket(BucketDetectionDto bucketDetectionDto) {
        BucketDetectionDataDto bucketDetectionDataDto = new BucketDetectionDataDto();
        TransmissionControl plc1 = TransmissionServer.transmissionControl;
        int a = plc1.bucketCheck(bucketDetectionDto);
        if (a != 99) {
            bucketDetectionDataDto.setTarget(bucketDetectionDto.getTarget());
            bucketDetectionDataDto.setFull(a);
            JsonData jsonData = new JsonData(true, "200", "操作成功", bucketDetectionDataDto);
            return jsonData;
        } else {
            JsonData jsonData = new JsonData(false, "500", "操作失败", null);
            return jsonData;
        }
    }
    //endregion

    //endregion

    @PostMapping("/data1")
    @ResponseBody
    public Object tranmissionData1() {
        TransmissionControl transmissionControl = TransmissionServer.transmissionControl;
        if (transmissionControl == null) {
            return null;
        }
        return transmissionControl.transmissionDriver.boilerDataMap;
    }

    @PostMapping("/data2")
    @ResponseBody
    public Object tranmissionData2() {
        TransmissionControl transmissionControl = TransmissionServer.transmissionControl;
        if (transmissionControl == null) {
            return null;
        }
        return transmissionControl.transmissionDriver.controlMap;
    }
}
