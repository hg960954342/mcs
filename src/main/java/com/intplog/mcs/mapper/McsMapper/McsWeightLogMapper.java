package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsWeightLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: YouName
 * @create: 2020-03-03 17:30
 **/
public interface McsWeightLogMapper {
    /**
     * @return
     */
    @Select({"<script>",
            "select id,create_time as createTime ,weight,weight_id as weightId,content from mcs_weight_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time like CONCAT(CONCAT('%',#{createTime}),'%')",
            "</if>",
            "<if test='weightId!=null'>",
            "and weightId=#{weightId}",
            "</if>",
            "order by create_time desc",
            "</script>"
    })
    List<McsWeightLog> getLists(@Param("createTime") String createTime,@Param("weightId")String weightId);

    /**
     *
     * @param createTime
     * @return
     */
    @Select({"<script>",
            "select id,create_time as createTime ,weight ,weight_id as weightId,content from mcs_weight_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time like CONCAT(CONCAT('%',#{createTime}),'%')",
            "</if>",
            "<if test='weightId!=null'>",
            "and weightId=#{weightId}",
            "</if>",
            "order by create_time desc",
            "</script>"
    })
    List<McsWeightLog> getListExcel(@Param("createTime") String createTime,@Param("weightId")String weightId);

    @Select("SELECT id,create_time as createTime ,weight ,weight_id as weightId,content FROM mcs_weight_log WHERE" +
            " id=#{id} order by create_time desc")
    McsWeightLog getMcsLogById(@Param("id") String id);

    /**
     * @param mcsLog
     * @return
     */
    @Insert("insert into mcs_weight_log (id,create_time ,weight ,weight_id , content) values " +
            "(#{id}, #{createTime}, #{weight},#{weightId},#{content})")
    int inserts(McsWeightLog mcsLog);

    /**
     * 更新数据
     *
     * @param mcsLog
     * @return
     */
    @Update("update mcs_weight_log set create_time=#{createTime},content=#{content},weight=#{weight},weight_id= " +
            "#{weightId} where id=#{id}")
    int updateByPrimaryKeys(McsWeightLog mcsLog);

    /**
     * 查询全部数据
     *
     * @return
     */
    @Select("SELECT id,create_time as createTime,weight , weight_id as weightId, content FROM mcs_weight_log order by create_time desc")
    List<McsWeightLog> getAll();


    /**
     * 删除指定日期之前的日志记录
     *
     * @param date
     * @return
     */
    @Delete("delete from mcs_weight_log where create_time < #{date}")
    int deleteLogs(@Param("date") Date date);
}
