package com.intplog.mcs.bean.model.WmsModel;

import lombok.*;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/11 14:37
 */
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class EquipCommand {
    private String taskId;
    private String point;
    private String command;
    private String dateTime;

//    /**
//     *任务号
//     */
//    private String id;
//
//    /**
//     *控制节点
//     */
//    private String point;
//
//    /**
//     *控制指令，0关闭，1开启
//     */
//    private String command;
//
//    /**
//     *发送时间2016/5/9 13:09:55
//     */
//    private Date dateTime;

}
