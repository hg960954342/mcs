package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import com.intplog.mcs.bean.viewmodel.PageData;

import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 14:35
 **/
public interface McsWeightProperTiesService {
    PageData getAll(String name, String connectId, int pageNum, int pageSize);
    List<McsWeightProperTies> getAllList();

    List<McsWeightProperTies> getBcrName(String name, String connectId);

    PageData deleteBcrConnectId(String id);

    int updateMcsBcr(McsWeightProperTies mcsBcrProperties);

    McsWeightProperTies getBcrId(String id);

    McsWeightProperTies getByConnectId(String connectId);

    int batchInsert(List<McsWeightProperTies> mcsBcrProperties);

    int insert(McsWeightProperTies mcsWeightProperties);

    McsWeightProperTies getByIp(String ip);
}
