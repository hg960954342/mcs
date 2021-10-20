package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsRfProperties;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 10:48
 */
public interface McsRfPropertiesMapper {

    /**
     * 查询全部信息
     * @date 2020/9/10 11:02
     * @author szh
     * @param
     * @return java.util.List<com.intplog.mcs.mapper.McsMapper.McsRfProperties>
     */
    @Select("select * from mcs_rf_properties order by name")
    List<McsRfProperties> getAll();

    /**
     * 插入信息
     * @date 2020/9/10 11:02
     * @author szh
     * @param mcsRfProperties
     * @return int
     */
    @Insert("insert into mcs_rf_properties(id,name,connect_id,ip,remark,type) values (#{id},#{name},#{connectId},#{ip},#{remark},#{type})")
    int insertMcsRfProperties(McsRfProperties mcsRfProperties);

    /**
     * 更新信息
     * @date 2020/9/10 11:02
     * @author szh
     * @param mcsRfProperties
     * @return int
     */
    @Update("update mcs_rf_properties set ip=#{ip},name=#{name},connect_id=#{connectId},type=#{type},remark=#{remark} where id=#{id}")
    int updateMcsRfProperties(McsRfProperties mcsRfProperties);

    /**
     * 删除信息
     * @date 2020/9/10 11:02
     * @author szh
     * @param mcsRfProperties
     * @return int
     */
    @Delete("delete from mcs_rf_properties where id=#{id}")
    int deleteMcsRfProperties(McsRfProperties mcsRfProperties);


}
