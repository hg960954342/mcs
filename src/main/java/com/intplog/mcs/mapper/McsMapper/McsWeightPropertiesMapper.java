package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 14:09
 **/
public interface McsWeightPropertiesMapper {

    /**
     * @param name
     * @param connectId
     * @return
     */
    @Select({
            "<script>",
            "select * from mcs_weight_properties where 1=1",
            "<if test='name!=null'>",
            "and name like CONCAT(CONCAT('%',#{name}),'%')",
            "</if>",
            "<if test='connectId!=null'>",
            "and connect_id like CONCAT(CONCAT('%',#{connectId}),'%')",
            "</if>",
            "</script>"
    })
    List<McsWeightProperTies> getBcrList(@Param("name") String name, @Param("connectId") String connectId);

    /**
     * 添加BCR编号
     *
     * @param record
     * @return
     */
    @Insert("insert into mcs_weight_properties(id,name,connect_id,ip,remark,type) values (#{id},#{name},#{connectId},#{ip},#{remark},#{type})")
    int insert(McsWeightProperTies record);

    @Select("select * from  mcs_weight_properties order by name")
    List<McsWeightProperTies> getAll();

    /**
     * 查询BCR编号
     *
     * @param name
     * @return
     */
    @Select({
            "<script>",
            "select * from mcs_weight_properties where 1=1",
            "<if test='name!=null'>",
            "and name = #{name})",
            "</if>",
            "<if test='connectId!=null'>",
            "and connect_id =#{connectId})",
            "</if>",
            "</script>"
    })
    List<McsWeightProperTies> getBcrName(@Param("name") String name, @Param("connectId") String connectId);

    /**
     * 删除bcr连接编号
     *
     * @param id
     * @return
     */
    @Delete("delete from mcs_weight_properties where id = #{id}")
    int deleteBcrConnectId(@Param("id") String id);

    /**
     * @param mcsBcrProperties
     * @return
     */
    @Update("update mcs_weight_properties set name = #{name}, connect_id=#{connectId},ip=#{ip},remark=#{remark},type=#{type} where id=#{id}")
    int updateMcsBcrProperties(McsWeightProperTies mcsBcrProperties);

    /**
     * @param id
     * @return
     */
    @Select("select * from mcs_weight_properties where id = #{id}")
    McsWeightProperTies getMcsBcrId(@Param("id") String id);

    /**
     * @param connectId
     * @return
     */
    @Select("select * from mcs_weight_properties where connect_id = #{connectId}")
    McsWeightProperTies getByConnectId(@Param("connectId") String connectId);

    @Insert("<script>" +
            "insert into mcs_weight_properties(id,name,connect_id,ip,remark)" +
            "values" +
            "<foreach collection='roleUserList' item='roleAcl' separator=',' >" +
            "(#{roleAcl.id}, #{roleAcl.name}, #{roleAcl.connectId},#{roleAcl.ip}, #{roleAcl.remark})" +
            "</foreach>" +
            "</script>")
    int excelInsert(@Param("roleUserList") List<McsWeightProperTies> roleUserList);

    /**
     * @param ip
     * @return
     */
    @Select("select * from mcs_weight_properties where ip = #{ip}")
    McsWeightProperTies getByIp(@Param("ip") String ip);
}
