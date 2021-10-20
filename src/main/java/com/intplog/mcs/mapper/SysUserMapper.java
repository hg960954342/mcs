package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liaoliming
 * @create 2019-08-22
 */

public interface SysUserMapper {


        /**
         * 添加用户信息
         *
         * @param record
         * @return
         */
        @Insert("insert into sys_user (id, user_name, telephone, mail, password, dept_id, status, remark, operator, operate_time, operate_ip, sex, role) " +
                "values (#{id}, #{userName}, #{telephone}, #{mail}, #{password}, #{deptId}, #{status}, #{remark}, #{operator}, #{operateTime}, #{operateIp}, #{sex}, #{role})")
        public int insert(SysUser record);


        /**
         * 查询用户是否存在
         *
         * @param id
         * @return
         */
        @Select("select id,user_name as userName,telephone,mail,password,dept_id as deptId,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,sex,role from sys_user where id = #{id}")
        public SysUser selectByPrimaryKey(@Param("id") Integer id);


        /**
         * 更新用户信息
         *
         * @param record
         * @return
         */
        @Update("update sys_user set user_name = #{userName},telephone = #{telephone},mail = #{mail},password = #{password},dept_id = #{deptId},status = #{status},remark = #{remark}, operator = #{operator}, operate_time = #{operateTime}, operate_ip = #{operateIp}, sex=#{sex}, role=#{role} where id = #{id}")
        public int updateByPrimaryKey(SysUser record);

        /**
         * 查询满足条件的用户信息
         *
         * @param keyword
         * @return
         */
        @Select("select id,user_name as userName,telephone,mail,password,dept_id as deptId,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,sex,role from sys_user where telephone = #{keyword} or user_name = #{keyword}")
        SysUser findByKeyword(@Param("keyword") String keyword);

        /**
         * 查询满足条件的用户个数
         *
         * @param mail
         * @param id
         * @return
         */
        @Select("select count(*) from sys_user where mail = #{mail} and id != #{id}")
        int countByMail(@Param("mail") String mail, @Param("id") Integer id);

        /**
         * 查询满足条件的用户个数
         *
         * @param telephone
         * @param id
         * @return
         */
        @Select("select count(*) from sys_user where telephone = #{telephone} and id != #{id}")
        int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

        /**
         * 查询满足条件的用户个数(分页)
         *
         * @param deptId
         * @return
         */
        @Select("select count(*) from sys_user where dept_id = #{deptId}")
        int countByDeptId(@Param("deptId") int deptId);

        /**
         * 查询满足条件的用户信息(分页)
         *
         * @param deptId
         * @param page
         * @return
         */
        @Select("select id,user_name as userName,telephone,mail,password,dept_id as deptId,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,sex,role from sys_user where dept_id = #{deptId} order by user_name asc limit #{page.offset},#{page.pageSize}")
        List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);

        @Select("<script>" +
                "select id,user_name as userName,telephone,mail,password,dept_id as deptId,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,sex,role  from sys_user where id in <foreach item='id' collection='idList' open='(' separator=',' close=')'> #{id} </foreach>" +
                "</script>")
        List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

        @Select("select id,user_name as userName,telephone,mail,password,dept_id as deptId,status,remark,operator,operate_time as operateTime,operate_ip as operateIp,sex,role  from sys_user")
        List<SysUser> getAll();

        /**
         * 删除用户信息
         *
         * @param id
         * @return
         */
        @Delete("delete from sys_user where id=#{id}")
        public int deleteUserInfoById(String id);
}
