package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 14:06
 */
@Getter
@Setter
@ToString
public class McsWalkRouteConfig {

    /**
     * id
     */
    private String id;

    /**
     * 设备编号
     */
    private String point;

    /**
     * 目的地址
     */
    private String target;

    /**
     * 路径编码
     */
    private int route;

    /**
     * 类型
     */
    private String type;
}
