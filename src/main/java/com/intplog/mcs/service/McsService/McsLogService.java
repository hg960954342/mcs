package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsLog;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 9:32
 */
public interface McsLogService {
    PageData getAll(String createTime, int pageNum, int pageSize);

    McsLog getMcsLogById(String id);

    int deleteMcsLog(int day);

    int insertMcsLog(McsLog mcsLog);

    int updateMcsLog(McsLog mcsLog);

    List<McsLog> getListExcel(String createTime);
}
