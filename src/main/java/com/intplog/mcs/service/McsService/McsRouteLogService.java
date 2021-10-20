package com.intplog.mcs.service.McsService;

import com.intplog.mcs.bean.model.McsModel.McsRouteLog;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/21 16:57
 */
public interface McsRouteLogService {
    List<McsRouteLog> getAll();
    List<McsRouteLog> getByContainer(String container);
    int insert(McsRouteLog mcsRouteLog);
    int deleteLogs(int day);

}
