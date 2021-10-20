package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-09 12:38
 **/
@Setter
@Getter
@ToString
public class McsTaskAlarmReport {
    /**
     * 主键
     */
    private  String id ;
    /**
     * 任务号
     */
    private String taskId ;
    /**
     * 任务类型
     */
    private  int type;

    /**
     * 母托盘
     */
    private String stockId ;
    /**
     * 入库口（原位置）
     */
    private String source;
    /**
     * 目的位置
     */
    private String target;
    /**
     * 入库重量
     */
    private String weight;
    /**
     * 优先级
     */
    private int priority;
    /**
     * 任务状态
     */
    private  int status;
    /**
     * 错误类型，根据错误列表匹配
     */
    private  int errorType;
    /**
     * 错误内用
     */
    private  String msg;
    /**
     * 上报状态
     */
    private  int reportStatus;
    /**
     * 生成时间
     */
    private Date createTime;
}
