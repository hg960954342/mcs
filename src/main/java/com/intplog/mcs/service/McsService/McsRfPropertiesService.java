package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsRfProperties;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 11:03
 */
public interface McsRfPropertiesService {

    List<McsRfProperties> getAll();

    int insertMcsRfProperties(McsRfProperties mcsRfProperties);

    int updateMcsRfProperties(McsRfProperties mcsRfProperties);

    int deleteMcsRfProperties(McsRfProperties mcsRfProperties);
}
