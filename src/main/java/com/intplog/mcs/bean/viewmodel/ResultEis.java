package com.intplog.mcs.bean.viewmodel;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * EIS-RCS 响应JSON串基类
 * @author liaoliming
 * @Date 2019-06-11 17:18
 */
@Getter
@Setter
@ToString
public class ResultEis {

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * true:成功 false:失败
     */
    private Boolean success;

    private String message;

    /**
     * 200：响应成功
     */
    private String code;

    private Object data;

}
