package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.LedAlarmLog;
import com.intplog.mcs.bean.viewmodel.PageData;

/**
 * @author liaoliming
 * @Date 2019-10-10 09:34
 * @Version 1.0
 */
public interface LedAlarmLogService {

        PageData getAll(String createTime, int pageNum, int pageSize);

        int deleteLog(int day);

        int insertLedAlarmLog(LedAlarmLog ledAlarmLog);

        int updateLedAlarmLog(LedAlarmLog ledAlarmLog);

}
