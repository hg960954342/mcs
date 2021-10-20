package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 10:49
 */
@Getter
@Setter
@ToString
public class McsRfProperties {

    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 连接编号
     */
    private String connectId;

    /**
     * ip地址信息
     */
    private String ip;

    /**
     * 备注
     */
    private String remark;

    /**
     * 类型
     */
    private int type;
}
