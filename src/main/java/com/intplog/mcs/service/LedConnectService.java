package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.LedConnect;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-11 09:50
 * @Version 1.0
 */
public interface LedConnectService {

        List<LedConnect> getExcelExport(String id, String name);

        PageData getAll(String id, String name, int pageNum, int pageSize);

        List<LedConnect> getAll();

        LedConnect getLedConnectById(String Id);

        int insertLedConnect(LedConnect ledConnect);

        int batchInsert(List<LedConnect> ledConnectList);

        int updateLedConnect(LedConnect ledConnect);

        PageData deleteLedConnectById(String Id);

}
