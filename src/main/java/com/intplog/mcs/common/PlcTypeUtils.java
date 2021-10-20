package com.intplog.mcs.common;

import com.intplog.siemens.enumerate.SiemensPLCS;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-09 17:15
 **/
public class PlcTypeUtils {


    /**
     *读取plc配置信息来获取PLC类型
     * @param type
     * @return
     */
    public static SiemensPLCS getPlcType(int type){
        SiemensPLCS siemensPLCS =null;
        switch (type){
            case 1 :
                siemensPLCS=SiemensPLCS.S1500;
                break;
            case 2:
                siemensPLCS= SiemensPLCS.S1200;
                break;
            case 3:
                siemensPLCS=SiemensPLCS.S400;
                break;
            case 4:
                siemensPLCS=SiemensPLCS.S300;
                break;
            case 5:
                siemensPLCS=SiemensPLCS.S200;
                break;
            case 6:
                siemensPLCS=SiemensPLCS.S200Smart;
                break;
                default:
                    siemensPLCS=SiemensPLCS.S1500;
        }
        return  siemensPLCS;
    }
}
