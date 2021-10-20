package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsWalkRouteConfig;
import com.intplog.mcs.bean.model.McsModel.McsWalkTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 14:15
 */
public interface McsWalkRouteConfigMapper {

    @Select("select * from mcs_walk_route_config")
    List<McsWalkRouteConfig> getAll();

}
