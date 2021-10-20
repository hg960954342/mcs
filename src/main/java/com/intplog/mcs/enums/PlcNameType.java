package com.intplog.mcs.enums;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/5/21 10:17
 */
public enum PlcNameType {
    Transmission(1, "输送线"),
    Hoist(2,"提升机"),
    EmergencyDoor(3,"安全门");

    private final int value;
    private final String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    PlcNameType(int value, String desc) {
        this.value=value;
        this.desc=desc;
    }

    public static PlcNameType ofValue(Integer target){
        for (PlcNameType type:values()){
            if (type.value==target) {
                return type;
            }
        }
        return null;
    }
}
