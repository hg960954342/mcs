package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.LedConnect;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-10 17:22
 * @Version 1.0
 */
public interface LedConnectMapper {


    /**
     * 模糊查询
     * @return
     */
    @Select({"<script>",
            "select id,name,ip,port,remark from led_connect where 1=1",
            "<if test='ledConId!=null'>",
            "and id=#{ledConId}",
            "</if>",
            "<if test='ledConName!=null'>",
            "and name=#{ledConName}",
            "</if>",
            "</script>"
    })
    public List<LedConnect> getList(@Param("ledConId") String id, @Param("ledConName") String name);

    @Select("SELECT id,name,ip,port,remark FROM led_connect")
    public List<LedConnect> getAll();

    /**
     *  根据id查询
     * @param id
     * @return
     */
    @Select({"<script>",
            "SELECT id,name,ip,port,remark FROM led_connect WHERE 1=1",
            "<if test='ledConId!=null'>",
            "and id=#{ledConId}",
            "</if>",
            "</script>"
    })
    public LedConnect getLedConnectById(@Param("ledConId") String id);

    /**
     * 添加led连接信息
     *
     * @param ledConnect
     * @return
     */
    @Insert("insert into led_connect (id, name, ip, port, remark) values (#{id}, #{name}, #{ip}, #{port}, #{remark})")
    public int insertLedConnect(LedConnect ledConnect);

    /**
     * 批量添加led连接信息
     * @param ledConnectList
     * @return
     */
    @Insert("<script>" +
            "insert into led_connect (id, name, ip, port, remark)" +
            "values" +
            "<foreach collection='ledConnectList' item='ledConnect' separator=',' >" +
            "(#{ledConnect.id}, #{ledConnect.name}, #{ledConnect.ip}, #{ledConnect.port}, #{ledConnect.remark})" +
            "</foreach>" +
            "</script>")
    public int batchInsert(@Param("ledConnectList") List<LedConnect> ledConnectList);

    /**
     * 更新Led连接信息
     *
     * @param ledConnect
     * @return
     */
    @Update("update led_connect set name=#{name},ip=#{ip},port=#{port},remark=#{remark} where id=#{id}")
    public int updateLedConnect(LedConnect ledConnect);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Delete("delete from led_connect where id=#{ledConId}")
    public int deleteLedConnect(@Param("ledConId") String id);

}
