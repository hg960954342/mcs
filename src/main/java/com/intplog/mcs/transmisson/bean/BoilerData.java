package com.intplog.mcs.transmisson.bean;

import lombok.Data;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/13 13:02
 */
@Data
public class BoilerData {
    private String group;
    private int arrive;
    private int compelete;

    public BoilerData(){

    }

    public BoilerData(String group, int arrive, int compelete) {
        this.group = group;
        this.arrive = arrive;
        this.compelete = compelete;
    }
}
