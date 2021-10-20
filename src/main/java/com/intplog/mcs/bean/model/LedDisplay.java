package com.intplog.mcs.bean.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class LedDisplay {

        /**
         * 编号 主键
         */
        @Excel(name = "编号",width = 15)
        private String id;
        /**
         * 内容
         */
        @Excel(name = "显示内容",width = 15)
        private String content;
        /**
         * 显示格式
         */
        @Excel(name = "显示格式",width = 15)
        private String displayFormat;
        /**
         * 状态1:未显示 2:已显示
         */
        @Excel(name = "状态",width = 15)
        private Integer status;
        /**
         * 创建时间
         */
        @Excel(name = "创建时间",width = 15)
        private Date createTime;
        /**
         * 更新时间
         */
        @Excel(name = "更新时间",width = 15)
        private Date updateTime;

}
