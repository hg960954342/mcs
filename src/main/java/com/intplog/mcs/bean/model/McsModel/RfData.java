package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/9 17:53
 */
@Getter
@Setter
@ToString
public class RfData {

    /**
     * 采集器编号
     */
    private String bcrId;

    /**
     * 桶编号
     */
    private String container;

    public RfData(String bcrId, String container)  {
        this.bcrId = bcrId;
        this.container = container;
    }

    public RfData() {
    }
}
