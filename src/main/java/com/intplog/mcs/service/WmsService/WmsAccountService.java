package com.intplog.mcs.service.WmsService;

import com.intplog.mcs.common.WmsJsonData;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/8 16:11
 */
public interface WmsAccountService {

    /**
     * 空盘释放信号
     * @param object
     * @return
     */
    WmsJsonData emptyBoxRelease(Object object);

    /**
     * RF上报
     * @param object
     * @return
     */
    WmsJsonData rfReport(Object object);

    /**
     * 重量上报
     * @param object
     * @return
     */
    WmsJsonData weighReport(Object object);

    /**
     * 翻倒完成桶数据
     * @param object
     * @return
     */
    WmsJsonData boxTurnOver(Object object);

    /**
     * 翻倒完成桶数据
     * @param object
     * @return
     */
    WmsJsonData staticWeight(Object object);

}
