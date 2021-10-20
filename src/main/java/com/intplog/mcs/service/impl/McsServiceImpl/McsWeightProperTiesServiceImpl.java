package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.McsMapper.McsWeightPropertiesMapper;
import com.intplog.mcs.service.McsService.McsWeightProperTiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 14:37
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class McsWeightProperTiesServiceImpl implements McsWeightProperTiesService {

    @Resource
    private McsWeightPropertiesMapper mcsWeightPropertiesMapper;
    @Override
    public PageData getAll(String name, String connectId, int pageNum, int pageSize) {
        PageData pd = new PageData();
        Page<Object>page= PageHelper.startPage(pageNum, pageSize);
        List<McsWeightProperTies>mcsWeightProperties= mcsWeightPropertiesMapper.getBcrList(name,connectId);
        pd.setMsg("");
        pd.setCode(0);
        pd.setCount(page.getTotal());
        pd.setData(mcsWeightProperties);
        return pd;
    }

    @Override
    public List<McsWeightProperTies> getAllList() {
        return mcsWeightPropertiesMapper.getAll();
    }

    @Override
    public List<McsWeightProperTies> getBcrName(String name, String connectId) {
        return mcsWeightPropertiesMapper.getBcrName(name,connectId);
    }

    @Override
    public PageData deleteBcrConnectId(String id) {
        PageData pd = new PageData();
        int i = mcsWeightPropertiesMapper.deleteBcrConnectId(id);
        pd.setCode(0);
        pd.setMsg("");
        if (i < 1) {
            pd.setMsg("删除数据失败！");
        } else {
            pd.setMsg("删除数据成功");

        }
        return pd;
    }

    @Override
    public int updateMcsBcr(McsWeightProperTies mcsBcrProperties) {
        return mcsWeightPropertiesMapper.updateMcsBcrProperties(mcsBcrProperties);
    }

    @Override
    public McsWeightProperTies getBcrId(String id) {
        return mcsWeightPropertiesMapper.getMcsBcrId(id);
    }

    @Override
    public McsWeightProperTies getByConnectId(String connectId) {
        return mcsWeightPropertiesMapper.getByConnectId(connectId);
    }

    @Override
    public int batchInsert(List<McsWeightProperTies> mcsBcrProperties) {
        return mcsWeightPropertiesMapper.excelInsert(mcsBcrProperties);
    }

    @Override
    public int insert(McsWeightProperTies mcsWeightProperties) {
        return mcsWeightPropertiesMapper.insert(mcsWeightProperties);
    }

    @Override
    public McsWeightProperTies getByIp(String ip) {
        return mcsWeightPropertiesMapper.getByIp(ip);
    }
}
