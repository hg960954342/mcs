package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 9:29
 */
public interface McsLogMapper {
    /**
     * @return
     */
    @Select({"<script>",
            "select id,create_time as createTime,type, content from mcs_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time like CONCAT(CONCAT('%',#{createTime}),'%')",
            "</if>",
            "order by create_time",
            "</script>"
    })
    List<McsLog> getLists(@Param("createTime") String createTime);

    /**
     * @param createTime
     * @return
     */
    @Select({"<script>",
            "select id,create_time as createTime,type, content  from mcs_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time =#{createTime}",
            "</if>",
            "order by create_time",
            "</script>"
    })
    List<McsLog> getListExcel(@Param("createTime") String createTime);

    @Select("SELECT id,create_time as createTime,type, content   FROM mcs_log WHERE id=#{id}" +
            " order by create_time")
    McsLog getMcsLogById(@Param("id") String id);

    /**
     * @param mcsLog
     * @return
     */
    @Insert("insert into mcs_log (id, create_time, type,  content  ) values (#{id}, #{createTime}, #{type},  #{content})")
    int inserts(McsLog mcsLog);

    /**
     * 更新数据
     *
     * @param mcsLog
     * @return
     */
    @Update("update mcs_log set create_time=#{createTime},type=#{type},content=#{content}  where id=#{id}")
    int updateByPrimaryKeys(McsLog mcsLog);

    /**
     * 查询全部数据
     *
     * @return
     */
    @Select("SELECT id,create_time as createTime,type, content  FROM mcs_log order by create_time desc")
    List<McsLog> getAll();


    /**
     * 删除指定日期之前的日志记录
     *
     * @param date
     * @return
     */
    @Delete("delete from mcs_log where create_time < #{date}")
    int deleteLogs(@Param("date") Date date);
}
