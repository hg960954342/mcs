package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsPlcVariable;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/15 12:24
 */
public interface McsPlcVariableService {

    List<McsPlcVariable> getExcelExport(String group, String name);

    JsonData importExcel(List<McsPlcVariable> mcsPlcVariableList);

    PageData getAll(String name, String groupNumber, int pageNum, int pageSize);

    List<McsPlcVariable> getAll();

    List<McsPlcVariable> getByName(String plcName);

    List<String> getGroupByName(String plcName);

    McsPlcVariable getMcsPlcVariableById(String id);

    McsPlcVariable getByTypeAndCoord(int type,String coord);

    McsPlcVariable getByNmaeAndDirection(String PlcName,int direction);

    List<McsPlcVariable> getByTypeAndName(int type,String name);

    McsPlcVariable getByTypeAndNameAndCoord(int type,String name,String coord);

    List<McsPlcVariable> getByType(int type);

    List<McsPlcVariable> getByGroupAndName(String group,String name);

    List<McsPlcVariable> getByGroupAndType(String group,int type);

    int insertMcsPlcVariable(McsPlcVariable mcsPlcVariable);

    int updateMcsPlcVariable(McsPlcVariable mcsPlcVariable);

    PageData deleteMcsPlcVariableById(String id);
}
