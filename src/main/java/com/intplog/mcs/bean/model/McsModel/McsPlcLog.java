package com.intplog.mcs.bean.model.McsModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-02-22 15:34
 **/
@Getter
@Setter
@ToString
public class McsPlcLog {
    /**
     * 主键
     */
    @Excel(name = "主键",width = 15)
    private  String id ;
    /**
     * 任务编号
     */
    @Excel(name = "任务编号",width = 15)
    private  String taskId;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 15)
    private Date createTime;
    /**
     * 任务类型
     */
    @Excel(name = "任务类型",width = 15)
    private  int type;
    /**
     * 响应时间
     */
    @Excel(name = "响应时间",width = 15)
    private  Date rpTime;
    /**
     * 内容
     */
    @Excel(name = "内容",width = 15)
    private  String content;
    /**
     * 箱号
     */
    @Excel(name = "箱号",width = 15)
    private  String boxNum;

}
