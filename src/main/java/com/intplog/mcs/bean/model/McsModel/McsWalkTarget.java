package com.intplog.mcs.bean.model.McsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/10 11:52
 */
@Getter
@Setter
@ToString
public class McsWalkTarget {

    /**
     * 区域
     */
    private String connectId;

    /**
     * 节点
     */
    private String target;

}


