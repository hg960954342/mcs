package com.intplog.mcs.bean.model.McsModel;

import lombok.Data;

import java.util.Date;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/21 16:36
 */
@Data
public class McsRouteLog {
    private String point;
    private int route;
    private String container;
    private Date datetime;
    private boolean flag;

    public McsRouteLog() {
    }

    public McsRouteLog(String point, int route, String container, Date datetime, boolean flag) {
        this.point = point;
        this.route = route;
        this.container = container;
        this.datetime = datetime;
        this.flag = flag;
    }
}
