package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsWalkTarget;
import com.intplog.mcs.bean.model.McsModel.McsWalkTask;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 12:20
 */
public interface McsWalkTaskService {

    /**
     * 查询所有信息
     *
     * @return
     */
    List<McsWalkTask> getAll();

    /**
     * 查询对应rf信息
     *
     * @return
     */
    public List<McsWalkTarget> getTarget(String target);

    /**
     * 根据箱号查询最近任务
     *
     * @param containerNo
     * @return
     */
    McsWalkTask getByContainer(String containerNo);

    McsWalkTask getByMcsId(int mcsId);

    /**
     * 插入数据
     *
     * @param mcsWalkTask
     * @return
     */
    int insertMcsWalkTask(McsWalkTask mcsWalkTask);

    /**
     * 更新数据
     *
     * @param mcsWalkTask
     * @return
     */
    int updateMcsWalkTask(McsWalkTask mcsWalkTask);

    /**
     * 更新数据
     *
     * @param mcsWalkTask
     * @return
     */
    int updateMcsWalkTask1(McsWalkTask mcsWalkTask);

    /**
     * 根据任务号删除
     *
     * @param taskId
     * @return
     */
    int deleteByTaskId(String taskId);

    /**
     * 根据箱号删除
     *
     * @param containerNo
     * @return
     */
    int deleteByContainerNo(String containerNo);
}
