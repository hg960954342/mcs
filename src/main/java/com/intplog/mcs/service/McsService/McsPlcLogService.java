package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsPlcLog;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-02-22 16:22
 **/
public interface McsPlcLogService {
    PageData getAll(String createTime,String boxNum,String taskId, int pageNum, int pageSize);

    McsPlcLog getMcsLogById(String id);

    int deleteMcsLog(int day);

    int insertMcsLog(McsPlcLog mcsLog);

    int updateMcsLog(McsPlcLog mcsLog);

    List<McsPlcLog> getListExcel(String createTime,String boxNum,String taskId);
}
