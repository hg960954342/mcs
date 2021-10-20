package com.intplog.mcs.enums;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/4 12:15
 */
public enum ScanType {

    STR(1,""),
    AREA(2,""),
    NODE(3,"");

    private final int value;
    private final String desc;

    ScanType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ScanType ofValue(Integer target){
        for(ScanType status : values()){
            if(status.value == target){
                return status;
            }
        }
        return null;
    }
}
