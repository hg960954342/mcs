package com.intplog.mcs.bean.model.McsModel;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/9 17:12
 */

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * PLC连接
 */
@Getter
@Setter
@ToString
public class McsPlcConnect {
    /**
     * 编号
     */
    @Excel(name = "编号", width = 15)
    @NotBlank(message = "该字段不能为空")
    public String id;

    /**
     * ip地址
     */
    @Excel(name = "IP地址", width = 15)
    public String ip;

    /**
     * 名称
     */
    @Excel(name = "PLC名称", width = 15)
    public String plcName;

    /**
     * 类型 1:1200,2:300,3:400,4:1500,5:smart200,6:200
     */
    @Excel(name = "类型", width = 15)
    public int type;

    /**
     * 作用 1:输送线,2:提升机
     */
    @Excel(name = "作用",width = 15)
    public int function;

    @Override
    public boolean equals(Object o) {
        if (o instanceof McsPlcConnect) {
            McsPlcConnect mcsPlcConnect = (McsPlcConnect) o;
            return this.id.equals(mcsPlcConnect.id)
                    && this.ip.equals(mcsPlcConnect.ip)
                    && this.plcName.equals(mcsPlcConnect.plcName);
        }
        return super.equals(o);
    }
}
