package com.intplog.mcs.service.impl.McsServiceImpl;

import com.intplog.mcs.bean.model.McsModel.McsWalkTarget;
import com.intplog.mcs.bean.model.McsModel.McsWalkTask;
import com.intplog.mcs.mapper.McsMapper.McsWalkTaskMapper;
import com.intplog.mcs.service.McsService.McsWalkTaskService;
import com.intplog.mcs.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 12:26
 */
@Service
public class McsWalkTaskServiceImpl implements McsWalkTaskService {

    @Resource
    private McsWalkTaskMapper mcsWalkTaskMapper;

    @Override
    public List<McsWalkTask> getAll() {
        return mcsWalkTaskMapper.getAll();
    }

    @Override
    public List<McsWalkTarget> getTarget(String target) {
        return mcsWalkTaskMapper.getTarget(target);
    }

    @Override
    public McsWalkTask getByContainer(String containerNo) {
        return mcsWalkTaskMapper.getByContainer(containerNo);
    }

    @Override
    public McsWalkTask getByMcsId(int mcsId) {
        return mcsWalkTaskMapper.getByMcsId(mcsId);
    }

    @Override
    public int insertMcsWalkTask(McsWalkTask mcsWalkTask) {
        mcsWalkTask.setDateTime(new Date());
        mcsWalkTask.setMcsId(Integer.valueOf(StringUtil.getTaskCode()));
        mcsWalkTask.setStatus("1");
        return mcsWalkTaskMapper.insertMcsWalkTask(mcsWalkTask);
    }

    @Override
    public int updateMcsWalkTask(McsWalkTask mcsWalkTask) {
        mcsWalkTask.setDateTime(new Date());
        mcsWalkTask.setMcsId(Integer.valueOf(StringUtil.getTaskCode()));
        mcsWalkTask.setStatus("1");
        return mcsWalkTaskMapper.updateMcsWalkTask(mcsWalkTask);
    }

    @Override
    public int updateMcsWalkTask1(McsWalkTask mcsWalkTask) {
        mcsWalkTask.setDateTime(new Date());
//        mcsWalkTask.setMcsId(Integer.valueOf(StringUtil.getTaskCode()));
        mcsWalkTask.setStatus("10");
        return mcsWalkTaskMapper.updateMcsWalkTask(mcsWalkTask);
    }


    @Override
    public int deleteByTaskId(String taskId) {
        return mcsWalkTaskMapper.deleteByTaskId(taskId);
    }

    @Override
    public int deleteByContainerNo(String containerNo) {
        return mcsWalkTaskMapper.deleteByContainerNo(containerNo);
    }
}
