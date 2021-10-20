package com.intplog.mcs.transmisson.bean;

import lombok.Data;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/14 14:02
 */
@Data
public class BoilerTask {
    private String group;

    private int mcsId;

    public BoilerTask() {
    }

    public BoilerTask(String group, int mcsId) {
        this.group = group;
        this.mcsId = mcsId;
    }
}
