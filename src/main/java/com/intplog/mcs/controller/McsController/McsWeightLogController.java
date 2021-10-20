package com.intplog.mcs.controller.McsController;

import com.intplog.mcs.bean.model.McsModel.McsWeightLog;
import com.intplog.mcs.service.McsService.McsWeightLogService;
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
 * @create: 2020-03-14 13:47
 **/
@Controller
@RequestMapping("/mcsWeightLog")
public class McsWeightLogController {

    @Autowired
    private McsWeightLogService mcsWeightLogService;
    @GetMapping("/data")
    @ResponseBody
    public Object toData(String createTime, String weightId, int page, int limit) {
        String ctime = createTime.equals("") ? null : createTime;
        String id = weightId.equals("") ? null : weightId;
        return mcsWeightLogService.getAll(ctime, id , page, limit);
    }
    @GetMapping("/")
    public  Object toList(){
        return "mcsWeightLog/list";
    }
    @DeleteMapping("/{day}")
    public String deleteLog(@PathVariable("day") int day) {
        int i = mcsWeightLogService.deleteMcsLog(day);
        if (i > 0) {
            return "删除称重日志成功";
        } else {
            return "删除称重日志失败";
        }
    }

    @RequestMapping("/toAdd")
    @ResponseBody
    public String toAddLog(McsWeightLog mcs) {
        int i = mcsWeightLogService.insertMcsLog(mcs);
        if (i > 0) {
            return "添加称重日志成功";
        } else {
            return "添加称重日志失败";
        }
    }
    @GetMapping("export")
    public void exportExcel(HttpServletResponse response, String weightId, String createTime){
        String id=weightId.equals("")?null:weightId;
        String bcrCreateTime=createTime.equals("")?null:createTime;
        List<McsWeightLog> mcsWeightLogList=mcsWeightLogService.getListExcel(bcrCreateTime,id);
        ExcelUtils.exportExcel(mcsWeightLogList,"MCS称重日志","MCS称重日志", McsWeightLog.class,"MCS称重日志.xls",response);
    }
}
