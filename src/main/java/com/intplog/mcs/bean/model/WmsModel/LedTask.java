package com.intplog.mcs.bean.model.WmsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/11 13:55
 */
@Getter
@Setter
@ToString
public class LedTask {

    /**
     * 任务号
     */
    private String taskId;

    /**
     * led屏编号
     */
    private String ledId;

    /**
     * 显示内容
     */
    private String data;

    /**
     * 0 开启 1关闭
     */
    private int status;
}
