package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.LedDisplay;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @program: wcs
 * @description
 * @author: Tianlei
 * @create: 2019-10-09 17:19
 **/
public interface LedDisplayMapper {
        /**
         * 添加led屏显示数据
         *
         * @param ledDisplay
         * @return
         */
        @Insert("INSERT INTO led_display(id,content,display_format,status,create_time,update_time)VALUES(#{id}," +
                "#{content},#{displayFormat},#{status},#{createTime},#{updateTime})")
        public int insertLedDisplay(LedDisplay ledDisplay);

        /**
         * @param id         编号
         * @param updateTime 更新时间
         * @return
         */
        @Update("update led_display set status=2 ,update_time=#{updateTime} where id=#{id} ")
        public int updateLedDisplayById(@Param("id") String id, @Param("updateTime") Date updateTime);

        /**
         * 手动更改显示数据
         *
         * @param ledDisplay
         * @return
         */
        @Update("update led_display set status=#{status} ,update_time=#{updateTime},content=#{content},display_format=#{displayFormat}," +
                "create_time=#{createTime} where id=#{id}")
        public int updateLedDisPlay(LedDisplay ledDisplay);

        /**
         * 模糊查询所有数据
         *
         * @return
         */
        @Select({"<script>",
                "select id,content,display_format as displayFormat,status,create_time as ",
                "createTime, update_time as updateTime from led_display where 1=1",
                "<if test='id!=null'>",
                "and id like CONCAT(CONCAT('%',#{id}),'%')",
                "</if>",
                "<if test='status!=null'>",
                "and status like CONCAT(CONCAT('%',#{status}),'%')",
                "</if>",
                "</script>"})
        public List<LedDisplay> getList(@Param("id") String id, @Param("status") Integer status);

        /**
         * 删除指定日期之前的日志记录
         *
         * @param date
         * @return
         */
        @Delete("delete from led_display where create_time < #{date}")
        public int deleteLedDisplayLog(@Param("date") Date date);

        /**
         * 删除指定id的日志记录
         *
         * @param id
         * @return
         */
        @Delete("delete from led_display where id = #{id}")
        public int deleteLedDisplayById(@Param("id") String id);

        /**
         * 查询对应id数据是否存在
         *
         * @param id
         * @return
         */
        @Select("select id,content,display_format as displayFormat,status,create_time as createTime, update_time as updateTime from led_display " +
                "where id=#{id}")
        public LedDisplay selectByPrimaryId(@Param("id") String id);

        /**
         * 查询所有数据
         * @return
         */
        @Select("select id,content,display_format as displayFormat,status,create_time as createTime, update_time as updateTime from led_display")
        public List<LedDisplay> getAllList();

        /**
         * 批量导入数据
         * @param ledDisplayList
         * @return
         */
        @Insert("<script>" +
                "insert into led_display(id,content,display_format,status,create_time,update_time)" +
                "values" +
                "<foreach  collection='ledDisplayList' item='ledDisplay'  separator=','>" +
                "(#{ledDisplay.id},#{ledDisplay.content},#{ledDisplay.displayFormat},#{ledDisplay.status},#{ledDisplay.createTime},#{ledDisplay" +
                ".updateTime})" +
                "</foreach>" +
                "</script>")
        public int importLedDisplayList(@Param("ledDisplayList") List<LedDisplay>ledDisplayList);
}
