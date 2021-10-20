package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.InterfaceLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author JiangZhongXing
 * @create 2019-01-10 11:26 AM
 */
public interface InterfaceLogMapper {

        /**
         * @return
         */
        @Select({"<script>",
                "select id,method,rq_time as rqTime,rq_data as rqData,rp_time as rpTime,status from mcs_interface_log where 1=1",
                "<if test='method!=null'>",
                "and method like CONCAT(CONCAT('%',#{method}),'%')",
                "</if>",
                "<if test='rqTime!=null'>",
                "and rq_time like CONCAT(CONCAT('%',#{rqTime}),'%')",
                "</if>",
                "</script>"
        })
        public List<InterfaceLog> getList(@Param("method") String method, @Param("rqTime") String rqTime);

        /**
         * 根据Id查询数据
         *
         * @param id
         * @return
         */
        @Select("select id,method,rq_time as rqTime,rq_data as rqData,rp_time as rpTime,status from mcs_interface_log where id=#{id}")
        public List<InterfaceLog> getInterfaceLogById(String id);

        /**
         * 查询全部
         *
         * @return
         */
        @Select("select id,method,rq_time as rqTime,rq_data as rqData,rp_time as rpTime,status from mcs_interface_log")
        public List<InterfaceLog> getAll();

        /**
         * 插入日志
         *
         * @param log
         * @return
         */
        @Insert("insert into mcs_interface_log(id,method,rq_time,rq_data,rp_time,rp_data,status) values(#{id},#{method},#{rqTime},#{rqData},#{rpTime},#{rpData},#{status})")
        public int insertInterfaceLog(InterfaceLog log);

        /**
         * 删除指定日期之前的日志记录
         *
         * @param date
         * @return
         */
        @Delete("delete from mcs_interface_log where rq_time < #{date}")
        public int deleteLog(@Param("date") Date date);

}
