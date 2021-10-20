package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsPlcConnect;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/12 14:26
 */
public interface McsPlcConnectService {

    List<McsPlcConnect> getExcelExport(String id, String name);

    PageData getAll(String ip, String name, int pageNum, int pageSize);

    List<McsPlcConnect> getAll();

    McsPlcConnect getMcsPlcConnectById(String id);

    List<McsPlcConnect> getByFunction(int function);

    int insertMcsPlcConnect(McsPlcConnect mcsPlcConnect);

    int updateMcsPlcConnect(McsPlcConnect mcsPlcConnect);

    PageData deleteMcsPlcConnectById(String id);

    JsonData importExcel(List<McsPlcConnect> mcsPlcConnectList);
}
