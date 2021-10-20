package com.intplog.mcs.bean.model.McsModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 13:44
 **/
@Getter
@Setter
@ToString
public class McsWeightProperTies {
    /**
     * 编号
     */
    @Excel(name = "编号", width=15)
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称",width = 15)
    private String name;

    /**
     * 连接编号
     */
    @Excel(name = "连接编号",width = 15)
    private String connectId;
    /**
     * 称IP地址
     */
    @Excel(name = "称IP地址",width = 15)
    private String ip;

    /**
     * 称IP地址
     */
    @Excel(name = "称端口号",width = 15)
    private int port;

    /**
     * 备注
     */
    @Excel(name = "备注",width = 15)
    private String remark;

    /**
     * 类型
     */
    @Excel(name = "类型",width = 15)
    private String type;
}
