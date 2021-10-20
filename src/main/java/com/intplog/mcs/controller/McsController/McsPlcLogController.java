package com.intplog.mcs.controller.McsController;

import com.intplog.mcs.bean.model.McsModel.McsPlcLog;
import com.intplog.mcs.bean.model.McsModel.McsWeightLog;
import com.intplog.mcs.service.McsService.McsPlcLogService;
import com.intplog.mcs.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-03-14 15:04
 **/
@Controller
@RequestMapping("/mcsPlcLog")
public class McsPlcLogController {
    @Autowired
    private McsPlcLogService mcsPlcLogService;

    @GetMapping("/data")
    @ResponseBody
    public Object toData(String createTime, String boxNum,String taskId, int page, int limit) {
        String ctime = createTime.equals("") ? null : createTime;
        String id = boxNum.equals("") ? null : boxNum;
        String task=taskId.equals("")?null:taskId;
        return mcsPlcLogService.getAll(ctime, id,task, page, limit);
    }

    @GetMapping("/")
    public Object toList() {
        return "mcsPlcLog/list";
    }

    @DeleteMapping("/{day}")
    public String deleteLog(@PathVariable("day") int day) {
        int i = mcsPlcLogService.deleteMcsLog(day);
        if (i > 0) {
            return "删除PLC日志成功";
        } else {
            return "删除PLC日志失败";
        }
    }

    @RequestMapping("/toAdd")
    @ResponseBody
    public String toAddLog(McsPlcLog mcs) {
        int i = mcsPlcLogService.insertMcsLog(mcs);
        if (i > 0) {
            return "添加PLC日志成功";
        } else {
            return "添加PLC日志失败";
        }
    }

    @GetMapping("export")
    public void exportExcel(HttpServletResponse response, String boxNum,String taskId, String createTime) {
        String id = taskId.equals("") ? null : taskId;
        String box = boxNum.equals("") ? null : boxNum;
        String bcrCreateTime = createTime.equals("") ? null : createTime;
        List<McsPlcLog> mcsPlcLogList = mcsPlcLogService.getListExcel(bcrCreateTime, box,id);
        ExcelUtils.exportExcel(mcsPlcLogList, "MCSPLC日志", "MCSPLC日志", McsPlcLog.class, "MCSPLC日志.xls", response);
    }
}
