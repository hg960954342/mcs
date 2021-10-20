package com.intplog.mcs.bean.model.McsModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 17:21
 **/
@Getter
@Setter
@ToString
public class McsWeightLog {

    /**
     * 主键
     */
    @Excel(name = "主键",width = 15)
    private String id ;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 15)
    private Date createTime;
    /**
     * 重量
     */
    @Excel(name = "重量",width = 15)
    private String weight;
    /**
     * 称重设备编号
     */
    @Excel(name = "称重设备编号",width = 15)
    private String weightId;
    /**
     * 备注
     */
    @Excel(name = "内容",width = 15)
    private String content;

}
