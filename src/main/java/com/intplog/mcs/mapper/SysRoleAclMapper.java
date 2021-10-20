package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysRoleAcl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */
public interface SysRoleAclMapper {

        int deleteByPrimaryKey(Integer id);

        int insert(SysRoleAcl record);

        int insertSelective(SysRoleAcl record);

        SysRoleAcl selectByPrimaryKey(Integer id);

        int updateByPrimaryKeySelective(SysRoleAcl record);

        int updateByPrimaryKey(SysRoleAcl record);

        @Select("<script>" +
                "select acl_id as aclId from sys_role_acl where role_id in <foreach item='roleId' collection='roleIdList' open='(' separator=',' close=')'> #{roleId} </foreach>" +
                "</script>")
        List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

        @Delete("delete from sys_role_acl where role_id = #{roleId}")
        void deleteByRoleId(@Param("roleId") int roleId);

        @Insert("<script>" +
                "insert into sys_role_acl (role_id, acl_id, operator, operate_time, operate_ip)" +
                "values" +
                "<foreach collection='roleAclList' item='roleAcl' separator=',' >" +
                "(#{roleAcl.roleId}, #{roleAcl.aclId}, #{roleAcl.operator}, #{roleAcl.operateTime}, #{roleAcl.operateIp})" +
                "</foreach>" +
                "</script>")
        void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

        @Select("select role_id as roleId from sys_role_acl where acl_id = #{aclId}")
        List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);
}