package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.LedAlarmLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-10 09:08
 * @Version 1.0
 */
public interface LedAlarmLogMapper {

        /**
         * @return
         */
        @Select({"<script>",
                "select id,create_time as createTime,type,content from led_alarm_log where 1=1",
                "<if test='createTime!=null'>",
                "and create_time like CONCAT(CONCAT('%',#{createTime}),'%')",
                "</if>",
                "</script>"
        })
        public List<LedAlarmLog> getList(@Param("createTime") String createTime);

        /**
         * @param alarmLog
         * @return
         */
        @Insert("insert into led_alarm_log (id, create_time, type, content) values (#{id}, #{createTime}, #{type}, #{content})")
        public int insert(LedAlarmLog alarmLog);

        /**
         * 更新数据
         *
         * @param alarmLog
         * @return
         */
        @Update("update led_alarm_log set create_time=#{createTime},type=#{type},content=#{content} where id=#{id}")
        public int updateByPrimaryKey(LedAlarmLog alarmLog);

        /**
         * 查询全部数据
         *
         * @return
         */
        @Select("SELECT id,create_time as createTime,type,content FROM led_alarm_log")
        public List<LedAlarmLog> getAll();


        /**
         * 删除指定日期之前的日志记录
         *
         * @param date
         * @return
         */
        @Delete("delete from led_alarm_log where create_time < #{date}")
        public int deleteLog(@Param("date") Date date);
}
