package com.intplog.mcs.enums;

/**
 * @author liaoliming
 * @Date 2019-11-06 18:00
 * 枚举类：回告wcs-eis设备类型
 */
public enum TaskType {

    InTask(1,"入库任务"),
    OutTask(2,"出库任务"),
    MoveTask(3,"移库任务"),
    CrossLayer(4,"换层任务"),
    Walk(5,"输送线行走任务"),
    MatBoxIn(6,"料箱进站"),
    OrderBoxIn(7,"订单箱进站"),
    ShapeInspect(8,"体积检测"),
    InBank(9,"入库口投箱口"),
    MatBoxArrive(10,"订单箱到位"),
    OrderBoxArrive(11,"料箱到位");

    private final int value;
    private final String desc;

    TaskType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static TaskType ofValue(Integer target){
        for(TaskType status : values()){
            if(status.value == target){
                return status;
            }
        }
        return null;
    }

}
