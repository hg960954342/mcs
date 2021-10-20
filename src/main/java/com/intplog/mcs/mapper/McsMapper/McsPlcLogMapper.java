package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsPlcLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-02-22 17:26
 **/
public interface McsPlcLogMapper {
    /**
     * @return
     */
    @Select({"<script>",
            "select id,task_id as taskId ,create_time as createTime,type ,rp_time as rpTime,content ,box_num as boxNum from mcs_plc_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time like CONCAT(CONCAT('%',#{createTime}),'%')",
            "</if>",
            "<if test='boxNum!=null'>",
            "and box_num= #{boxNum}",
            "</if>",
            "<if test='taskId!=null'>",
            "and task_id= #{taskId}",
            "</if>",
            "order by create_time desc",
            "</script>"
    })
    List<McsPlcLog> getLists(@Param("createTime") String createTime,@Param("boxNum")String boxNum,@Param("taskId")String taskId);

    /**
     *
     * @param createTime
     * @return
     */
    @Select({"<script>",
            "select id,task_id as taskId ,create_time as createTime,type ,rp_time as rpTime,content ,box_num as boxNum from mcs_plc_log where 1=1",
            "<if test='createTime!=null'>",
            "and create_time =#{createTime}",
            "</if>",
            "<if test='boxNum!=null'>",
            "and box_num= #{boxNum}",
            "</if>",
            "<if test='taskId!=null'>",
            "and task_id= #{taskId}",
            "</if>",
            "order by create_time desc" ,
            "</script>"
    })
    List<McsPlcLog> getListExcel(@Param("createTime") String createTime,@Param("boxNum")String boxNum,@Param("taskId")String taskId);

    @Select("SELECT id,task_id as taskId ,create_time as createTime,type ,rp_time as rpTime,content ,box_num as boxNum FROM mcs_plc_log WHERE " +
            "id=#{id} order by create_time desc")
    McsPlcLog getMcsLogById(@Param("id") String id);

    /**
     * @param mcsLog
     * @return
     */
    @Insert("insert into mcs_plc_log (id,task_id,create_time,type,rp_time ,content , box_num) values " +
            "(#{id},#{taskId}, #{createTime}, #{type},#{rpTime},#{content},#{boxNum})")
    int inserts(McsPlcLog mcsLog);

    /**
     * 更新数据
     *
     * @param mcsLog
     * @return
     */
    @Update("update mcs_plc_log set task_id=#{taskId} ,create_time=#{createTime},type=#{type},rp_time=#{rpTime},content=#{content},box_num= " +
            "#{boxNum} where id=#{id}")
    int updateByPrimaryKeys(McsPlcLog mcsLog);

    /**
     * 查询全部数据
     *
     * @return
     */
    @Select("SELECT id,task_id as taskId ,create_time as createTime,type ,rp_time as rpTime,content ,box_num as boxNum " +
            "FROM mcs_plc_log order by create_time desc")
    List<McsPlcLog> getAll();


    /**
     * 删除指定日期之前的日志记录
     *
     * @param date
     * @return
     */
    @Delete("delete from mcs_plc_log where create_time < #{date}")
    int deleteLogs(@Param("date") Date date);
}
