package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.InterfaceLog;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;

import java.util.Date;
import java.util.List;

/**
 * 日志接口
 *
 * @author liaoliming
 * @Date 2019-06-14 09:28
 * @Version 1.0
 */
public interface InterfaceLogService {

        List<InterfaceLog> getInterfaceLogById(String id);

        PageData getAll(String method, String rqTime, int pageNum, int pageSize);

        int insertInterfaceLog(String method, Object rq, Date rqTime, JsonData rp, Date rpTime);
        int insertInterfaceLog(String method, Object rq, Date rqTime, Object rp, Date rpTime);
        int insertInterFaceLogByResPCross(String method, Object rq, Date rqTime, JsonData rp, Date rpTime);

        int deleteLog(int day);
}
