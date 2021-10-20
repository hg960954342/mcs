package com.intplog.mcs.weigh.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/7 16:00
 */
@Getter
@Setter
@ToString
public class WeighData {

    /**
     * 编号
     */
    private String number;

    /**
     * 类型（1动态秤，2静态秤）
     */
    private String type;

    /**
     * 重量
     */
    private double weight;
}
