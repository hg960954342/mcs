package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 11:52
 */
@Getter
@Setter
@ToString
public class McsWalkTask {

    /**
     * 任务号
     */
    @NotBlank(message = "任务号不能为空")
    private String taskId;


    private int mcsId;

    /**
     * 箱号
     */
    @NotBlank(message = "任务号不能为空")
    private String containerNo;

    /**
     * 目的位置
     */
    @NotBlank(message = "任务号不能为空")
    private String target;

//    /**
//     * 任务状态
//     */
//    private String status;

    /**
     * 库房
     */
    private String str;

    /**
     * 区域
     */
    private String area;

    /**
     * 节点
     */
    private String node;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 时间
     */
    private Date dateTime;

    /**
     * 类型
     */
    private int type;
}
