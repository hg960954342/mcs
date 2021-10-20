package com.intplog.mcs.service;

import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @author JiangZhongXing
 * @create 2019-01-11 10:33 AM
 */
public interface SysParamService {

        int getTotal();

        List<SysParam> getKeyAndValue();

        PageData getAll(String code, String name, int pageNum, int pageSize);

        SysParam getSysParamByCode(String code);

        int insertSysParam(SysParam sysParam);

        int updateSysParam(SysParam sysParam);

        int updateSysParamValue(String code, String value);

        PageData deleteSysParamBymId(String code);
}
