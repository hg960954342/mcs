package com.intplog.mcs.mapper.McsMapper;

import com.intplog.mcs.bean.model.McsModel.McsWalkTarget;
import com.intplog.mcs.bean.model.McsModel.McsWalkTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 12:00
 */
public interface McsWalkTaskMapper {

    /**
     * 查询所有信息
     *
     * @param
     * @return java.util.List<com.intplog.mcs.bean.model.McsModel.McsWalkTask>
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Select("select * from mcs_walk_task")
    List<McsWalkTask> getAll();

    /**
     * 查询所有信息
     *
     * @param
     * @return java.util.List<com.intplog.mcs.bean.model.McsModel.McsWalkTarget>
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Select("select * from mcs_walk_target_config where target=#{target}")
    List<McsWalkTarget> getTarget(@Param("target") String target);

    /**
     * 根据箱号查询最近任务
     *
     * @param containerNo
     * @return com.intplog.mcs.bean.model.McsModel.McsWalkTask
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Select("select * from mcs_walk_task where container_no=#{containerNo} order by date_time desc limit 1")
    McsWalkTask getByContainer(@Param("containerNo") String containerNo);


    @Select("select * from mcs_walk_task where mcs_id=#{mcsId} order by date_time desc limit 1")
    McsWalkTask getByMcsId(@Param("mcsId") int mcsId);

    /**
     * 插入数据
     *
     * @param mcsWalkTask
     * @return int
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Insert("insert into mcs_walk_task(task_id,mcs_id,container_no,target,str,area,node,status,date_time) " +
            "values (#{taskId},#{mcsId},#{containerNo},#{target},#{str},#{area},#{node},#{status},#{dateTime})")
    int insertMcsWalkTask(McsWalkTask mcsWalkTask);

//    @Insert("insert into mcs_walk_task(task_id,mcs_id,container_no,target,status,str,area,node,date_time) " +
//            "values (#{taskId},#{mcsId},#{containerNo},#{target},#{status},#{str},#{area},#{node},#{dateTime})")
//    int insertMcsWalkTask(McsWalkTask mcsWalkTask);

    /**
     * 更新数据
     *
     * @param mcsWalkTask
     * @return int
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Update("update mcs_walk_task set task_id=#{taskId},mcs_id=#{mcsId},container_no=#{containerNo},target=#{target},str=#{str}, " +
            "area=#{area},node=#{node},status=#{status},date_time=#{dateTime} where task_id=#{taskId}")
    int updateMcsWalkTask(McsWalkTask mcsWalkTask);

//    @Update("update mcs_walk_task set task_id=#{taskId},mcs_id=#{mcsId},container_no=#{containerNo},target=#{target},status=#{status},str=#{str}, " +
//            "area=#{area},node=#{node},date_time=#{dateTime} where task_id=#{taskId}")
//    int updateMcsWalkTask(McsWalkTask mcsWalkTask);

    /**
     * 根据任务号删除
     *
     * @param taskId
     * @return int
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Delete("delete from mcs_walk_task where task_id=#{taskId}")
    int deleteByTaskId(@Param("taskId") String taskId);

    /**
     * 根据箱号删除
     *
     * @param containerNo
     * @return int
     * @date 2020/9/10 12:19
     * @author szh
     */
    @Delete("delete from mcs_walk_task where container_no=#{containerNo}")
    int deleteByContainerNo(@Param("containerNo") String containerNo);
}
