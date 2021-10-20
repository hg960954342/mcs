package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsInterfaceLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author quqingmao
 * @date 2019-10-11
 */
public interface McsInterfaceLogMapper {

        /**
         * 添加数据
         *
         * @return
         */
        @Insert("insert into mcs_interface_log (id ,method,rq_time,rq_data,rp_data,rp_time,status) values (#{id} ,#{method},#{rqTime} ,#{rqData},#{rpData},#{rpTime},#{status})")
        public int insert(McsInterfaceLog mcsInterfaceLog);


        /**
         * 查询全部
         *
         * @return
         */
        @Select("SELECT id , method,rq_time as rqTime , rq_data as rqData,rp_data as rpData , rp_time as rpTime ,status " +
                "FROM mcs_interface_log order by rp_time desc ")
        List<McsInterfaceLog> getAll();


        /**
         * 根据Id查询数据
         *
         * @param id
         * @return
         */
        @Select("SELECT id , method,rq_time as rqTime , rq_data as rqData,rp_data as rpData , rp_time as rpTime ,status " +
                "FROM mcs_interface_log WHERE id=#{id} order by rp_time desc")
        public McsInterfaceLog getInterfaceLogById(@Param("id") String id);

        /**
         * @return
         */
        @Select({"<script>",
                "select id , method,rq_time as rqTime , rq_data as rqData,rp_data as rpData , rp_time as rpTime ,status ",
                "from mcs_interface_log where 1=1 ",
                "<if test='method!=null'>",
                "and method = #{method}",
                "</if>",
                "order by rp_time desc",
                "</script>"

        })
        List<McsInterfaceLog> getList(@Param("method") String method);

        /**
         * 删除指定日期之前的日志记录
         *
         * @return
         */
        @Delete("delete from mcs_interface_log where rq_time < #{date}")
        public int deleteLog(@Param("date") Date date);

        @Update("update mcs_interface_log set id =#{id}, method = #{method},rq_time = #{rqTime} , rq_data = #{rqData},rp_data = #{rpData} , rp_time = #{rpTime} ,status=#{status} where id = #{id}")
        int updateInterfaceLog(McsInterfaceLog mcsInterfaceLog);

}
