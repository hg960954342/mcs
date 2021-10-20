package com.intplog.mcs.bean.model;

import lombok.Data;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/7/7 1:32
 */
@Data
public class CompareModel {
    private int count;
    private Object msg;

    public CompareModel(int count, Object msg) {
        this.count = count;
        this.msg = msg;
    }

    public CompareModel() {
    }
}
