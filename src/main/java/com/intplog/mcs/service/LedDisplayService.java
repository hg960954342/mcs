package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.LedDisplay;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;

import java.util.Date;
import java.util.List;

/**
 * @program: wcs
 * @description
 * @author: Tianlei
 * @create: 2019-10-09 17:44
 **/
public interface LedDisplayService {
        int insertLedDisplay(LedDisplay ledDisplay);

        int updateLedDisplayById(String id, Date date);

        int updateLedDisplay(LedDisplay ledDisplay);

        int deleteLedDisplay(int day);

        PageData deleteLedDisplayById(String id);

        PageData getAll(String id, Integer status, int pageNum, int pageSize);

        LedDisplay getLedDisplayById(String id);
        List<LedDisplay> getAll();
        List<LedDisplay> getAllList(String id,Integer Status);
        JsonData insertImport(List<LedDisplay> ledDisplayList);
}
