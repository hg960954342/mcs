package com.intplog.mcs.service.impl.McsServiceImpl;

import com.intplog.mcs.bean.model.McsModel.McsRfProperties;
import com.intplog.mcs.mapper.McsMapper.McsRfPropertiesMapper;
import com.intplog.mcs.service.McsService.McsRfPropertiesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 11:11
 */
@Service
public class McsRfPropertiesServiceImpl implements McsRfPropertiesService {

    @Resource
    private McsRfPropertiesMapper mcsRfPropertiesMapper;

    @Override
    public List<McsRfProperties> getAll() {
        return mcsRfPropertiesMapper.getAll();
    }

    @Override
    public int insertMcsRfProperties(McsRfProperties mcsRfProperties) {
        return mcsRfPropertiesMapper.insertMcsRfProperties(mcsRfProperties);
    }

    @Override
    public int updateMcsRfProperties(McsRfProperties mcsRfProperties) {
        return mcsRfPropertiesMapper.updateMcsRfProperties(mcsRfProperties);
    }

    @Override
    public int deleteMcsRfProperties(McsRfProperties mcsRfProperties) {
        return mcsRfPropertiesMapper.deleteMcsRfProperties(mcsRfProperties);
    }
}
