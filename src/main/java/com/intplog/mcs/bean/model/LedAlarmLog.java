package com.intplog.mcs.bean.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author liaoliming
 * @Date 2019-10-09 16:49
 * led故障日志
 */
@Getter
@Setter
@ToString
public class LedAlarmLog {

    /**
     * 主键
     */
    private String id;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 故障类型
     */
    private String type;
    /**
     * 内容
     */
    private String content;

}
