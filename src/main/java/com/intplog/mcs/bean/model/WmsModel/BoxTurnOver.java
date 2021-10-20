package com.intplog.mcs.bean.model.WmsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 9:41
 */
@Getter
@Setter
@ToString
public class BoxTurnOver {

    /**
     * 夹层锅编号
     */
    private String potcode;

    /**
     * 桶编号
     */
    private String container;

    public BoxTurnOver() {
    }

    public BoxTurnOver(String potcode, String container) {
        this.potcode = potcode;
        this.container = container;
    }
}
