package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsLog;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.McsMapper.McsLogMapper;
import com.intplog.mcs.service.McsService.McsLogService;
import com.intplog.mcs.utils.DateHelpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 9:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class McsLogServiceImpl implements McsLogService {
    @Resource
    private McsLogMapper mcsLogMapper;

    @Override
    public PageData getAll(String createTime, int pageNum, int pageSize) {
        PageData pd = new PageData();
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<McsLog> all = mcsLogMapper.getLists(createTime);
        pd.setMsg("");
        pd.setCount(page.getTotal());
        pd.setCode(0);
        pd.setData(all);
        return pd;
    }

    @Override
    public McsLog getMcsLogById(String id) {
        return mcsLogMapper.getMcsLogById(id);
    }

    @Override
    public int deleteMcsLog(int day) {
        Date dt = DateHelpUtil.getDate(day);
        return mcsLogMapper.deleteLogs(dt);
    }

    @Override
    public int insertMcsLog(McsLog mcsLog) {
        return mcsLogMapper.inserts(mcsLog);
    }

    @Override
    public int updateMcsLog(McsLog mcsLog) {
        return mcsLogMapper.updateByPrimaryKeys(mcsLog);
    }

    @Override
    public List<McsLog> getListExcel(String createTime) {
        return mcsLogMapper.getListExcel(createTime);
    }

}
