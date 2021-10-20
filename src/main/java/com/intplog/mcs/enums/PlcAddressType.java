package com.intplog.mcs.enums;

/**
 * @program: mcs
 * @description
 * @author: tianlei
 * @create: 2020-03-17 18:04
 **/
public enum PlcAddressType {

    EXIT(1, "出入口"),
    CrossLayer(2, "换层任务"),
    SIZE(3, "尺寸检测"),
    InBankApplication(4, "输送线入库申请口"),
    DBPoint(5, "订单箱BCR暂存区就绪"),
    PickStationLoad(6, "拣选站载货"),
    HoistIn(7, "提升机入库侧滚筒（料箱入库）"),
    HoistOut(8, "提升机出库侧滚筒（料箱出库）"),
    RequestApplication(13, "申请任务"),
    ContainerNo(14, "条码"),
    RequestCode(15, "请求序列"),
    Load(16, "载货状态"),
    BUTTON(17, "按钮信号"),
    OPENDOOR(18,"安全门允许开"),
    Target(20, "目的位置"),
    ComfireCode(21, "确认序列"),
    ShelfRollerIn1(23, "货架端入库辊筒工位1取货完成"),
    ShelfRollerOut2(24, "货架端出库辊筒工位2接受上位"),
    TransmissionInBank1(25, "输送线进提升机等待位1号位"),
    TransmissionInBank2(26, "输送线进提升机等待位2号位"),
    TransmissionOutBank(27, "提升机出到输送线等待点"),
    ReadTaskId(28, "PLC写入任务号");


    private final int value;
    private final String desc;

    PlcAddressType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static PlcAddressType ofValue(Integer target) {
        for (PlcAddressType status : values()) {
            if (status.value == target) {
                return status;
            }
        }
        return null;
    }
}
