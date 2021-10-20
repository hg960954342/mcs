package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsRouteLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/21 16:41
 */
public interface McsRouteLogMapper {

    /**
     * 查询全部信息
     *
     * @return
     */
    @Select("select * from mcs_route_log order by datetime desc")
    List<McsRouteLog> getAll();

    @Select("select * from mcs_route_log where container=#{container} order by datetime desc")
    List<McsRouteLog> getByContainer(@Param("container") String container);

    @Insert("insert into mcs_route_log(point,route,container,datetime,flag) values(#{point},#{route},#{container},#{datetime},#{flag})")
    int insert(McsRouteLog mcsRouteLog);

    @Delete("delete from mcs_route_log where create_time < #{date}")
    int deleteLogs(@Param("date") Date date);
}
