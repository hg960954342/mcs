package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsWeightLog;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 17:35
 **/
public interface McsWeightLogService {

    PageData getAll(String createTime,String weightId, int pageNum, int pageSize);

    McsWeightLog getMcsLogById(String id);

    int deleteMcsLog(int day);

    int insertMcsLog(McsWeightLog mcsLog);

    int updateMcsLog(McsWeightLog mcsLog);

    List<McsWeightLog> getListExcel(String createTime,String weightId);
    //跟据称重信息记录日志
    void insert(String weight ,String address);

}
