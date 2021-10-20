package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsPlcVariable;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.mapper.McsMapper.McsPlcVariableMapper;
import com.intplog.mcs.service.McsService.McsPlcVariableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/15 13:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class McsPlcVariableServiceImpl implements McsPlcVariableService {

    @Resource
    private McsPlcVariableMapper mcsPlcVariableMapper;

    @Override
    public List<McsPlcVariable> getExcelExport(String group, String name) {
        return mcsPlcVariableMapper.getList(group, name);
    }

    @Override
    public JsonData importExcel(List<McsPlcVariable> mcsPlcVariableList) {
        List<McsPlcVariable> plcVariableList = mcsPlcVariableMapper.getAll();
        List<String> idList = plcVariableList.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<String> ipList = plcVariableList.stream().map(a -> a.getAddress()).collect(Collectors.toList());
        if (mcsPlcVariableList.size() > 0) {
            for (int i = 0; i < mcsPlcVariableList.size(); i++) {
                McsPlcVariable mcsPlcVariable = mcsPlcVariableList.get(i);
                if (idList.contains(mcsPlcVariable.getId()) || ipList.contains(mcsPlcVariable.getAddress())) {
                    return JsonData.fail(MessageFormat.format("导入数据第{0}行编号和IP地址已存在", i + 1), mcsPlcVariable.id);
                }
            }
            int i = mcsPlcVariableMapper.importList(mcsPlcVariableList);
            if (i > 0) {
                return JsonData.success(MessageFormat.format("导入数据成功，受影响行数{0}", mcsPlcVariableList.size()));
            } else {
                return JsonData.fail("2");
            }
        } else {
            return JsonData.fail("导入数据为空", "3");
        }
    }

    @Override
    public PageData getAll(String name, String groupNumber, int pageNum, int pageSize) {
        PageData pageData = new PageData();
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<McsPlcVariable> all = mcsPlcVariableMapper.getList(name, groupNumber);
        pageData.setMsg("");
        pageData.setCount(page.getTotal());
        pageData.setCode(0);
        pageData.setData(all);
        return pageData;
    }

    @Override
    public List<McsPlcVariable> getAll() {
        return mcsPlcVariableMapper.getAll();
    }

    @Override
    public List<McsPlcVariable> getByName(String plcName) {
        return mcsPlcVariableMapper.getByName(plcName);
    }

    @Override
    public List<String> getGroupByName(String plcName) {
        return mcsPlcVariableMapper.getGroupByName(plcName);
    }

    @Override
    public McsPlcVariable getMcsPlcVariableById(String id) {
        return mcsPlcVariableMapper.getMcsPlcVariableById(id);
    }

    @Override
    public McsPlcVariable getByTypeAndCoord(int type, String coord) {
        return mcsPlcVariableMapper.getByTypeAndCoord(type, coord);
    }

    @Override
    public McsPlcVariable getByNmaeAndDirection(String PlcName, int direction) {
        return mcsPlcVariableMapper.getByNameAndDirection(PlcName,direction);
    }


    @Override
    public List<McsPlcVariable> getByTypeAndName(int type, String name) {
        return mcsPlcVariableMapper.getByTypeAndName(type, name);
    }

    @Override
    public McsPlcVariable getByTypeAndNameAndCoord(int type, String name, String coord) {
        return mcsPlcVariableMapper.getByTypeAndNameAndCoord(type,name,coord);
    }

    @Override
    public List<McsPlcVariable> getByType(int type) {
        return mcsPlcVariableMapper.getByType(type);
    }

    @Override
    public List<McsPlcVariable> getByGroupAndName(String group, String name) {
        return mcsPlcVariableMapper.getByGroupAndName(group, name);
    }

    @Override
    public List<McsPlcVariable> getByGroupAndType(String group, int type) {
        return mcsPlcVariableMapper.getByGroupAndType(group,type);
    }


    @Override
    public int insertMcsPlcVariable(McsPlcVariable mcsPlcVariable) {
        return mcsPlcVariableMapper.insertMcsPlcVariable(mcsPlcVariable);
    }

    @Override
    public int updateMcsPlcVariable(McsPlcVariable mcsPlcVariable) {
        return mcsPlcVariableMapper.updateMcsPlcVariable(mcsPlcVariable);
    }

    @Override
    public PageData deleteMcsPlcVariableById(String id) {
        PageData pageData = new PageData();
        int i = mcsPlcVariableMapper.deleteMcsPlcVariableById(id);
        pageData.setMsg("");
        pageData.setCode(0);
        if (i < 1) {
            pageData.setMsg("更新失败");
        }
        return pageData;
    }

}
