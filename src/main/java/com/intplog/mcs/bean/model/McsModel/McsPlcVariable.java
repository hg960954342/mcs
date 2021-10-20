package com.intplog.mcs.bean.model.McsModel;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/9 17:16
 */

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * PLC配置
 */
@Getter
@Setter
@ToString
public class McsPlcVariable {

    /**
     * 编号
     */
    @Excel(name = "编号", width = 15)
    @NotBlank(message = "该字段不能为空")
    public String id;

    /**
     * 类型 1、直连 2、opc
     */
    @Excel(name = "类型", width = 15)
    public int type;

    /**
     * 名称
     */
    @Excel(name = "PLC名称", width = 15)
    public String plcName;

    /**
     * ip地址
     */
    @Excel(name = "IP地址", width = 15)
    public String address;

    /**
     * 刷新时间（单位：毫秒）
     */
    @Excel(name = "刷新时间", width = 15)
    public int refreshTime;

    /**
     * 组
     */
    @Excel(name = "组", width = 15)
    public String groupNumber;
    /**
     * 坐标
     */
    @Excel(name = "索引", width = 15)
    public String coord;

    @Excel(name = "方向", width = 15)
    public int direction;


    @Override
    public boolean equals(Object o) {
        if (o instanceof McsPlcVariable) {
            McsPlcVariable mcsPlcVariable = (McsPlcVariable) o;
            return this.id.equals(mcsPlcVariable.id)
                    && this.plcName.equals(mcsPlcVariable.plcName)
                    && this.address.equals(mcsPlcVariable.address)
                    && this.groupNumber.equals(mcsPlcVariable.groupNumber)
                    && this.coord.equals(mcsPlcVariable.coord);

        }
        return super.equals(o);
    }

}
