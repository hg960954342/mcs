package com.intplog.mcs.mapper;

import com.intplog.mcs.bean.model.SysParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author JiangZhongXing
 * @create 2019-01-10 5:59 PM
 */
public interface SysParamMapper {

        @Select({"<script>",
                "select * from sys_param where 1=1",
                "<if test='code!=null'>",
                "and code like CONCAT(CONCAT('%',#{code}),'%')",
                "</if>",
                "<if test='name!=null'>",
                "and name like CONCAT(CONCAT('%',#{name}),'%')",
                "</if>",
                "</script>"
        })
        public List<SysParam> getList(@Param("code") String code, @Param("name") String name);

        /**
         * 查询数据总条数
         */
        @Select("select count(*) from sys_param")
        public int getTotal();

        /**
         * 查询全部数据
         *
         * @return
         */
        @Select("SELECT * FROM sys_param ORDER BY sort")
        public List<SysParam> getAll();

        /**
         * 查询系统参数
         *
         * @param code
         * @return
         */
        @Select("SELECT * FROM sys_param WHERE code=#{code}")
        public SysParam getSysParamByCode(String code);

        /**
         * 添加系统参数
         *
         * @param sysParam
         * @return
         */
        @Insert("insert into sys_param(code,value,name,sort) values(#{code},#{value},#{name},#{sort})")
        public int insertSysParam(SysParam sysParam);

        /**
         * 修改系统参数
         *
         * @param sysParam
         * @return
         */
        @Update("update sys_param set value=#{value},name=#{name},sort=#{sort} where code=#{code}")
        public int updateSysParam(SysParam sysParam);

        /**
         * 更新值
         *
         * @param code
         * @param value
         * @return
         */
        @Update("update sys_param set value=#{value} where code=#{code}")
        public int updateSysParamValue(@Param("code") String code, @Param("value") String value);

        /**
         * 删除系统参数
         *
         * @param code
         * @return
         */
        @Delete("delete from sys_param where code=#{code}")
        public int deleteSysParam(String code);

}
