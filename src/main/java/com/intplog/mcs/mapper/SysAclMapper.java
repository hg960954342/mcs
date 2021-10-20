package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysAcl;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */
public interface SysAclMapper {

        int deleteByPrimaryKey(Integer id);

        @Insert("insert into sys_acl (id, code, name, acl_module_id, url, type, status, seq, remark, operator, operate_time, operate_ip) values (#{id}, #{code}, #{name}, #{aclModuleId}, #{url}, #{type}, #{status}, #{seq}, #{remark}, #{operator}, #{operateTime}, #{operateIp})")
        int insert(SysAcl record);

        int insertSelective(SysAcl record);

        @Select("select id,code,name,acl_module_id as aclModuleId,url,type,status,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_acl where id = #{id}")
        SysAcl selectByPrimaryKey(Integer id);

        @Update({"<script>",
                "update sys_acl set",
                "<if test='code!=null'>",
                "code = #{code},",
                "</if>",
                "<if test='name!=null'>",
                "name = #{name},",
                "</if>",
                "<if test='aclModuleId!=null'>",
                "acl_module_id = #{aclModuleId},",
                "</if>",
                "<if test='url!=null'>",
                "url = #{url},",
                "</if>",
                "<if test='type!=null'>",
                "type = #{type},",
                "</if>",
                "<if test='status!=null'>",
                "status = #{status},",
                "</if>",
                "<if test='seq!=null'>",
                "seq = #{seq},",
                "</if>",
                "<if test='remark!=null'>",
                "remark = #{remark},",
                "</if>",
                "<if test='operator!=null'>",
                "operator = #{operator},",
                "</if>",
                "<if test='operateTime!=null'>",
                "operate_time = #{operateTime},",
                "</if>",
                "<if test='operateIp!=null'>",
                "operate_ip = #{operateIp}",
                "</if>",
                "where id = #{id}",
                "</script>"})
        int updateByPrimaryKeySelective(SysAcl record);

        @Update("update sys_acl set code = #{code},name = #{name},acl_module_id = #{aclModuleId},url = #{url},type = #{type},status = #{status},seq = #{seq},remark = #{remark},operator = #{operator},operate_time = #{operateTime},operate_ip = #{operateIp} where id = #{id}")
        int updateByPrimaryKey(SysAcl record);

        @Select("select count(1) from sys_acl where acl_module_id = #{aclModuleId}")
        int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

        @Select("select id,code,name,acl_module_id as aclModuleId,url,type,status,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_acl where acl_module_id = #{aclModuleId} order by seq asc,name asc limit #{page.offset},#{page.pageSize}")
        List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);

        @Select("select count(1) from sys_acl where acl_module_id = #{aclModuleId} and name = #{name} and id != #{id}")
        int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

        @Select("select id,code,name,acl_module_id as aclModuleId,url,type,status,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_acl")
        List<SysAcl> getAll();

        @Select("<script>" +
                "select id,code,name,acl_module_id as aclModuleId,url,type,status,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_acl where id in <foreach item='id' collection='idList' open='(' separator=',' close=')'> #{id} </foreach>" +
                "</script>")
        List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

        //url is not null and url != '' and  #{url} REGEXP url-->
        @Select("select id,code,name,acl_module_id as aclModuleId,url,type,status,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_acl where url = #{url}")
        List<SysAcl> getByUrl(@Param("url") String url);


}