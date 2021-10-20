package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 接口日志数据表
 *
 * @author JiangZhongXing
 * @create 2019-01-10 11:23 AM
 */
@Getter
@Setter
@ToString
public class InterfaceLog {

    private String id;
    private String method;
    private Date rqTime;
    private String rqData;
    private Date rpTime;
    private String rpData;
    private Integer status;

}
