package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-09
 */
public interface SysRoleMapper {
        int deleteByPrimaryKey(Integer id);

        @Insert("insert into sys_role (id, name, type, status, remark, operator, operate_time, operate_ip) values (#{id}, #{name}, #{type}, #{status}, #{remark}, #{operator}, #{operateTime}, #{operateIp})")
        int insert(SysRole record);

        int insertSelective(SysRole record);

        @Select("select id, name, type, status, remark, operator, operate_time as operateTime, operate_ip as operateIp from sys_role where id = #{id}")
        SysRole selectByPrimaryKey(Integer id);

        int updateByPrimaryKeySelective(SysRole record);

        @Update("update sys_role set name = #{name},type = #{type},status = #{status},remark = #{remark},operator = #{operator},operate_time = #{operateTime},operate_ip = #{operateIp} where id = #{id}")
        int updateByPrimaryKey(SysRole record);

        @Select("select id, name, type, status, remark, operator, operate_time as operateTime, operate_ip as operateIp from sys_role")
        List<SysRole> getAll();

        @Select("select count(1) from sys_role where name = #{name} and id != #{id}")
        int countByName(@Param("name") String name, @Param("id") Integer id);

        @Select("<script>" +
                "select * from sys_role where id in <foreach item='id' collection='idList' open='(' separator=',' close=')'> #{id} </foreach>" +
                "</script>")
        List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}