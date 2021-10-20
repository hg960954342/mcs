package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsPlcLog;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.McsMapper.McsPlcLogMapper;
import com.intplog.mcs.service.McsService.McsPlcLogService;
import com.intplog.mcs.utils.DateHelpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-02-22 17:23
 **/
@Service
public class McsPlcLogServiceImpl implements McsPlcLogService {
    @Resource
    public McsPlcLogMapper plcLogMapper;

    @Override
    public PageData getAll(String createTime,String boxNum,String taskId, int pageNum, int pageSize) {
        PageData pd = new PageData();
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<McsPlcLog> all = plcLogMapper.getLists(createTime,boxNum,taskId);
        pd.setMsg("");
        pd.setCount(page.getTotal());
        pd.setCode(0);
        pd.setData(all);
        return pd;
    }

    @Override
    public McsPlcLog getMcsLogById(String id) {
        return plcLogMapper.getMcsLogById(id);
    }

    @Override
    public int deleteMcsLog(int day) {
        Date dt = DateHelpUtil.getDate(day);
        return plcLogMapper.deleteLogs(dt);
    }

    @Override
    public int insertMcsLog(McsPlcLog mcsLog) {
        return plcLogMapper.inserts(mcsLog);
    }

    @Override
    public int updateMcsLog(McsPlcLog mcsLog) {
        return plcLogMapper.updateByPrimaryKeys(mcsLog);
    }

    @Override
    public List<McsPlcLog> getListExcel(String createTime,String boxNum,String taskId) {
        return plcLogMapper.getListExcel(createTime,boxNum,taskId);
    }
}
