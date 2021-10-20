package com.intplog.mcs.bean.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JiangZhongXing
 * @create 2019-01-10 5:57 PM
 */

@Getter
@Setter
@ToString
public class SysParam {
    private String code;
    private String value;
    private String name;
    private Integer sort;

}
