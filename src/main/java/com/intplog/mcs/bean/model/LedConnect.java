package com.intplog.mcs.bean.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author liaoliming
 * @Date 2019-10-09 17:33
 */
@Getter
@Setter
@ToString
public class LedConnect {

    /**
     * 编号
     */
    @Excel(name = "编号", width=15)
    @NotBlank(message = "该字段不能为空")
    private String id;
    /**
     * 名称
     */
    @Excel(name = "名称", width=15)
    @NotBlank(message = "该字段不能为空")
    private String name;
    /**
     * ip地址
     */
    @Excel(name = "ip",width = 15)
    @NotBlank(message = "该字段不能为空")
    private String ip;
    /**
     * 端口号
     */
    @Excel(name = "端口号",width = 15)
    @NotBlank(message = "该字段不能为空")
    private String port;
    /**
     * 备注
     */
    @Excel(name = "备注",width = 15)
    private String remark;

    public LedConnect() {
    }

    public LedConnect(String id, String name, String ip, String port, String remark) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.remark = remark;
    }
}
