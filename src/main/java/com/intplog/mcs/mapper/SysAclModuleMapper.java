package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysAclModule;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-08
 */
public interface SysAclModuleMapper {
        /**
         * 查询满足条件的权限模块个数
         *
         * @param parentId
         * @param name
         * @param id
         * @return
         */
        @Select("select count(1) from sys_acl_module where name = #{name} and parent_id = #{parentId} and id != #{id}")
        int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

        /**
         * 保存
         *
         * @param record
         * @return
         */
        @Insert("insert into sys_acl_module (id, name, parent_id, level, seq, status, remark, operator, operate_time, operate_ip, img) values (#{id}, #{name}, #{parentId}, #{level}, #{seq}, #{status},#{remark}, #{operator}, #{operateTime},#{operateIp},#{img})")
        int insert(SysAclModule record);

        /**
         * 查询满足条件的权限模块个数
         *
         * @param id
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,img from sys_acl_module where id = #{id}")
        SysAclModule selectByPrimaryKey(Integer id);

        /**
         * 更新
         *
         * @param record
         * @return
         */
        @Update("update sys_acl_module set name = #{name}, parent_id = #{parentId}, level = #{level}, seq = #{seq}, status = #{status}, remark = #{remark}, operator = #{operator}, operate_time = #{operateTime}, operate_ip = #{operateIp},img=#{img} where id = #{id}")
        int updateByPrimaryKey(SysAclModule record);

        /**
         * 根据权限模块层级查询
         *
         * @param level
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,img from sys_acl_module where level like #{level}")
        List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

        /**
         * 更新
         *
         * @param sysAclModuleList
         */
        @Select("<foreach collection= 'sysAclModuleList' item='sysAclModule' separator=';'> " +
                "update sys_acl_module set level = #{level} where id = #{id}" +
                "</foreach>")
        void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

        /**
         * 全部查询
         *
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,img from sys_acl_module")
        List<SysAclModule> getAllAclModule();

        /**
         * 查询
         *
         * @param aclModuleId
         * @return
         */
        @Select(" select count(1) from sys_acl_module where parent_id = #{aclModuleId}")
        int countByParentId(@Param("aclModuleId") int aclModuleId);

        /**
         *
         */
        @Delete("delete from sys_acl_module where id = #{id}")
        int deleteByPrimaryKey(Integer id);
}
