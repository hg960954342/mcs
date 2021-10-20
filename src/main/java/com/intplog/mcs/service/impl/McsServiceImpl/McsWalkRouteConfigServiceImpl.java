package com.intplog.mcs.service.impl.McsServiceImpl;

import com.intplog.mcs.bean.model.McsModel.McsWalkRouteConfig;
import com.intplog.mcs.mapper.McsMapper.McsWalkRouteConfigMapper;
import com.intplog.mcs.service.McsService.McsWalkRouteConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 14:13
 */
@Service
public class McsWalkRouteConfigServiceImpl implements McsWalkRouteConfigService {

    @Resource
    private McsWalkRouteConfigMapper mcsWalkRouteConfigMapper;

    @Override
    public List<McsWalkRouteConfig> getAll() {
        return mcsWalkRouteConfigMapper.getAll();
    }
}
