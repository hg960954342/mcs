package com.intplog.mcs.service.impl.McsServiceImpl;

import com.intplog.mcs.bean.model.McsModel.McsRouteLog;
import com.intplog.mcs.mapper.McsMapper.McsRouteLogMapper;
import com.intplog.mcs.service.McsService.McsRouteLogService;
import com.intplog.mcs.utils.DateHelpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/21 17:04
 */
@Service
public class McsRouteLogServiceImpl implements McsRouteLogService {
    @Resource
    private McsRouteLogMapper mcsRouteLogMapper;
    @Override
    public List<McsRouteLog> getAll() {
        return mcsRouteLogMapper.getAll();
    }

    @Override
    public List<McsRouteLog> getByContainer(String container) {
        return mcsRouteLogMapper.getByContainer(container);
    }

    @Override
    public int insert(McsRouteLog mcsRouteLog) {
        return mcsRouteLogMapper.insert(mcsRouteLog);
    }

    @Override
    public int deleteLogs(int day) {
        Date date= DateHelpUtil.getDate(day);

        return mcsRouteLogMapper.deleteLogs(date);
    }
}
