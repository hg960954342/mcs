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
public class McsLog {

    /**
     * 编号
     */
    @Excel(name = "编号" , width = 15)
    private String id;

    /**
     * 生成时间
     */
    @Excel(name = "生成时间" , width = 15)
    private Date createTime;

    /**
     * 任务类型 1、周转箱关联 2、区域分拨 3、内复核分拨 4、补货分拨
     */
    @Excel(name = "任务类型",width = 15)
    private String type;

    /**
     * 内容
     */
    @Excel(name = "内容",width = 15)
    private String content;

}
