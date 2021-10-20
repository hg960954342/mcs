package com.intplog.mcs.enums;

public enum McsLogType {
    BCRCONNECT(1,"读码器连接"),
    WEIGHTCONNECT(2,"称重设备连接"),
    PLCCONNECT(3,"PLC连接"),
    RFCONNEVT(4,"RF设备连接");

    private final int value;
    private final String desc;

    McsLogType(int value ,String desc){
        this.value=value;
        this.desc= desc;
    }
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static McsLogType ofValue(Integer target){
        for(McsLogType status : values()){
            if(status.value == target){
                return status;
            }
        }
        return null;
    }
}
