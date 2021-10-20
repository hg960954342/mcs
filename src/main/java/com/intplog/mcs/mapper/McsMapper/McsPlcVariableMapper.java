package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsPlcVariable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/15 11:54
 */
public interface McsPlcVariableMapper {

    /**
     * 根据group、name查询信息
     *
     * @param
     * @param
     * @return
     */
    @Select({"<script>",
            "select * from mcs_plc_variable where 1=1",
            "<if test='groupNumber!=null'>",
            "and group_number=#{groupNumber}",
            "</if>",
            "<if test='name!=null'>",
            "and plc_name=#{plcName}",
            "</if>",
            "</script>"
    })
    public List<McsPlcVariable> getList(@Param("plcName") String plcName, @Param("groupNumber") String groupNumber);

    /**
     * 查询所有信息
     *
     * @return
     */
    @Select("select *  from mcs_plc_variable")
    public List<McsPlcVariable> getAll();

    /**
     * 根据id查询信息
     *
     * @param Id
     * @return
     */
    @Select("select *  from mcs_plc_variable where id=#{plcVarId}")
    public McsPlcVariable getMcsPlcVariableById(@Param("plcVarId") String Id);

    /**
     * 根据group和name查询信息
     *
     * @param group
     * @return
     */
    @Select("select *  from mcs_plc_variable where group_number=#{plcGroup} and plc_name=#{plcName}")
    public List<McsPlcVariable> getByGroupAndName(@Param("plcGroup") String group, @Param("plcName") String name);

    /**
     * 根据group和type查询信息
     * @param group
     * @param type
     * @return
     */
    @Select("select *  from mcs_plc_variable where group_number=#{plcGroup} and type=#{type}")
    public List<McsPlcVariable> getByGroupAndType(@Param("plcGroup") String group, @Param("type") int type);

    /**
     * 根据坐标和类型查询信息
     *
     * @param type
     * @param coord
     * @return
     */
    @Select("select id,type,name,address,refresh_time,group_number,coord,direction  from mcs_plc_variable" +
            " where coord=#{coord} and type=#{type}")
    public McsPlcVariable getByTypeAndCoord(@Param("type") int type, @Param("coord") String coord);


    /**
     * 根据PLC名称和方向
     *
     * @param name
     * @param direction
     * @return
     */
    @Select("select *  from mcs_plc_variable" +
            " where plc_name=#{name} and direction=#{direction}")
    public McsPlcVariable getByNameAndDirection(@Param("name") String name, @Param("direction") int direction);

    /**
     * 根据名称和类型查询信息
     *
     * @param type
     * @param name
     * @return
     */
    @Select("select * from mcs_plc_variable" +
            " where plc_name=#{plc_name} and type=#{type}")
    public List<McsPlcVariable> getByTypeAndName(@Param("type") int type, @Param("plc_name") String name);

    /**
     * 根据名称、类型和坐标查询信息
     * @param type
     * @param name
     * @param coord
     * @return
     */
    @Select("select *  from mcs_plc_variable" +
            " where name=#{name} and type=#{type} and coord=#{coord}")
    public McsPlcVariable getByTypeAndNameAndCoord(@Param("type") int type, @Param("name") String name, @Param("coord") String coord);

    /**
     * 根据类型查询信息
     *
     * @param type
     * @return
     */
    @Select("select id,type,plc_name,address,refresh_time,group_number,coord,direction  from mcs_plc_variable" +
            " where type=#{type}")
    public List<McsPlcVariable> getByType(@Param("type") int type);

    /**
     * 根据PLC名称查询信息
     *
     * @param
     * @return
     */
    @Select("select id,type,plc_name,address,refresh_time,group_number,coord,direction  from mcs_plc_variable" +
            " where plc_name=#{plc_name}")
    public List<McsPlcVariable> getByName(@Param("plc_name") String plcName);

    /**
     * 根据PLC名称查询信息
     *
     * @param
     * @return
     */
    @Select("select distinct group_number from mcs_plc_variable" +
            " where plc_name=#{plc_name}")
    public List<String> getGroupByName(@Param("plc_name") String plcName);

    /**
     * 添加PLC路径信息
     *
     * @param mcsPlcVariable
     * @return
     */
    @Insert("insert into mcs_plc_variable (id,type,name,address,refresh_time,group_number,coord,direction )" +
            " values(#{id},#{type},#{name},#{address},#{refreshTime},#{groupNumber},#{coord},#{direction})")
    public int insertMcsPlcVariable(McsPlcVariable mcsPlcVariable);

    /**
     * 跟新PLC路径信息
     *
     * @param mcsPlcVariable
     * @return
     */
    @Update("update mcs_plc_variable set type=#{type},name=#{name},address=#{address},refresh_time=" +
            "#{refreshTime},group_number=#{groupNumber},coord=#{coord},direction=#{direction} where id=#{id}")
    public int updateMcsPlcVariable(McsPlcVariable mcsPlcVariable);

    /**
     * 删除PLC路径信息
     *
     * @param id
     * @return
     */
    @Delete("delete from mcs_plc_variable where id=#{plcVarId}")
    public int deleteMcsPlcVariableById(@Param("plcVarId") String id);

    @Insert({"<script>",
            "insert into mcs_plc_variable (id,type,name,address,refresh_time,group_number,coord,direction )",
            "values",
            "<foreach collection='mcsPlcVariableList' item='mcsPlcVariable' separator=','>",
            "(#{mcsPlcVariable.id},#{mcsPlcVariable.type},#{mcsPlcVariable.name}," +
                    "#{mcsPlcVariable.address},#{mcsPlcVariable.refreshTime},#{mcsPlcVariable.groupNumber}" +
                    "#{mcsPlcVariable.coord},#{mcsPlcVariable.direction}",
            "</foreach>",
            "</script>"})
    public int importList(@Param("mcsPlcVariableList") List<McsPlcVariable> mcsPlcVariableList);
}
