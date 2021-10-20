package com.intplog.mcs.bean.model.McsModel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author quqingmao
 * @date 2019-10-09
 */
@Getter
@Setter
@ToString
public class McsInterfaceLog {

    /**
     * 编号
     */
    @Excel(name = "编号" ,width = 15)
    private String id;

    /**
     * 方法名称
     */
    @Excel(name = "方法名称",width = 15)
    private  String method;

    /**
     * 请求时间
     */
    @Excel(name = "请求时间",width = 15)
    private Date rqTime;

    /**
     * 请求信息
     */
    @Excel(name = "请求信息",width = 15)
    private String rqData;

    /**
     * 响应信息
     */
    @Excel(name = "响应信息",width = 15)
    private String rpData;

    /**
     * 响应时间
     */
    @Excel(name = "响应时间",width = 15)
    private  String rpTime;

    /**
     * 状态（1错误；0正常）
     */
    @Excel(name = "状态",width = 15)
    private int status;


}
