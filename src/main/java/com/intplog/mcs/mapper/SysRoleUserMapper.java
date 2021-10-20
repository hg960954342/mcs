package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleUserMapper {

        int deleteByPrimaryKey(Integer id);

        int insert(SysRoleUser record);

        int insertSelective(SysRoleUser record);

        SysRoleUser selectByPrimaryKey(Integer id);

        int updateByPrimaryKeySelective(SysRoleUser record);

        int updateByPrimaryKey(SysRoleUser record);

        @Select("select role_id as roleId from sys_role_user where user_id = #{userId}")
        List<Integer> getRoleIdListByUserId(@Param("userId") int userId);

        @Select("select user_id as userId from sys_role_user where role_id = #{roleId}")
        List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);

        @Delete("delete from sys_role_user where role_id = #{roleId}")
        void deleteByRoleId(@Param("roleId") int roleId);

        @Insert("<script>" +
                "insert into sys_role_user (id, role_id, user_id, operator, operate_time, operate_ip)" +
                "values" +
                "<foreach collection='roleUserList' item='roleAcl' separator=',' >" +
                "(#{roleAcl.id}, #{roleAcl.roleId}, #{roleAcl.userId}, #{roleAcl.operator}, #{roleAcl.operateTime}, #{roleAcl.operateIp})" +
                "</foreach>" +
                "</script>")
        void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

        @Select("<script>" +
                "select user_id as userId from sys_role_user where role_id in <foreach item='roleId' collection='roleIdList' open='(' separator=',' close=')'> #{roleId} </foreach>" +
                "</script>")
        List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}