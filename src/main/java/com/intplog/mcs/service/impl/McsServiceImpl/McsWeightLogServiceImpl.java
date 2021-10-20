package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsWeightLog;
import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.McsMapper.McsWeightLogMapper;
import com.intplog.mcs.service.McsService.McsWeightLogService;
import com.intplog.mcs.service.McsService.McsWeightProperTiesService;
import com.intplog.mcs.utils.DateHelpUtil;
import com.intplog.mcs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 17:44
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class McsWeightLogServiceImpl implements McsWeightLogService {
    @Resource
    private McsWeightLogMapper mcsWeightLogMapper;
    @Autowired
    private McsWeightProperTiesService mcsWeightProperTiesService;
    @Override
    public PageData getAll(String createTime,String weightId, int pageNum, int pageSize) {
        PageData pd = new PageData();
        Page<Object>pg= PageHelper.startPage(pageNum,pageSize);
        List<McsWeightLog>logList= mcsWeightLogMapper.getLists(createTime,weightId);
        pd.setMsg("");
        pd.setCode(0);
        pd.setCount(pg.getTotal());
        pd.setData(logList);
        return pd;
    }

    @Override
    public McsWeightLog getMcsLogById(String id) {
        return mcsWeightLogMapper.getMcsLogById(id);
    }

    @Override
    public int deleteMcsLog(int day) {
        Date date= DateHelpUtil.getDate(day);
        return mcsWeightLogMapper.deleteLogs(date);
    }

    @Override
    public int insertMcsLog(McsWeightLog mcsLog) {
        return mcsWeightLogMapper.inserts(mcsLog);
    }

    @Override
    public int updateMcsLog(McsWeightLog mcsLog) {
        return mcsWeightLogMapper.updateByPrimaryKeys(mcsLog);
    }

    @Override
    public List<McsWeightLog> getListExcel(String createTime,String weightId) {
        return mcsWeightLogMapper.getLists(createTime,weightId);
    }

    @Override
    public void insert(String weight, String ip) {
        McsWeightProperTies mcsWeightProperties= mcsWeightProperTiesService.getByIp(ip);
        McsWeightLog mcsWeightLog = new McsWeightLog();
        mcsWeightLog.setId(StringUtil.getUUID32());
        mcsWeightLog.setContent(mcsWeightProperties.getName());
        mcsWeightLog.setCreateTime(new Date());
        mcsWeightLog.setWeight(weight);
        mcsWeightLog.setWeightId(mcsWeightProperties.getConnectId());
        mcsWeightLogMapper.inserts(mcsWeightLog);
    }
}
