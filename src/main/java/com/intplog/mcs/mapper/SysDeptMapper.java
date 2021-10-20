package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysDept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-05
 */
public interface SysDeptMapper {

        /**
         * 删除部门信息
         *
         * @param id
         * @return
         */
        @Delete("delete from sys_dept where id = #{id}")
        public int deleteByPrimaryKey(@Param("id") Integer id);

        /**
         * 添加部门信息
         *
         * @param record
         * @return
         */
        @Insert("insert into sys_dept (id, name, parentId,level, seq, remark,operator, operateTime, operateIp) " +
                "values (#{id}, #{name}, #{parentId},#{level}, #{seq}, #{remark},#{operator}, #{operateTime}, #{operateIp})")
        public int insert(SysDept record);

        /**
         * 查询部门是否存在
         *
         * @param id
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_dept where id = #{id}")
        public SysDept selectByPrimaryKey(@Param("id") Integer id);


        /**
         * 查询全部部门
         *
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_dept")
        public List<SysDept> getAllDept();


        /**
         * 更新部门信息
         *
         * @param record
         * @return
         */
        @Update("update sys_dept set name = #{name},parentId = #{parentId},level = #{level},seq = #{seq},remark = #{remark},operator = #{operator},operateTime = #{operateTime},operateIp = #{operateIp} where id = #{id}")
        public int updateByPrimaryKey(SysDept record);


        /**
         * 根据部门层级查询部门信息
         *
         * @param
         * @return
         */
        @Select("select id,name,parent_id as parentId,level,seq,remark,operator,operate_time as operateTime,operate_ip as operateIp from sys_dept where level like #{level} || '.%'")
        public List<SysDept> getChildDeptListByLevel(@Param("level") String level);


        /**
         * 根据部门id批量更新level
         */
        @Update({
                "<script>",
                "<foreach collection='sysDeptList' item='sysDept' separator=';'>",
                "update sys_dept",
                "set level = #{dept.level}",
                "where id = #{dept.id}",
                "</foreach>",
                "</script>"
        })
        void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);

        /**
         * 根据上级部门id,部门名称，部门id查询满足条件的部门数量
         */
        @Select({"<script>",
                "SELECT count(1) FROM sys_dept",
                "WHERE name = #{name}",
                "<when test='parentId!=null'>",
                "AND parentId = #{parentId}",
                "</when>",
                "<when test='id!=null'>",
                "AND id != #{id}",
                "</when>",
                "</script>"})
        public int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

        /**
         * 查询
         *
         * @param deptId
         * @return
         */
        @Select("SELECT count(1) FROM sys_dept WHERE parentId = #{deptId}")
        int countByParentId(@Param("deptId") int deptId);
}
