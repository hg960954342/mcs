package com.intplog.mcs.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2021/3/15 11:22
 */
@Data
public class WmsJsonData {

    private boolean isCompression;

    private String message;

    private int stateCode;

    private Object data;

    private String errorMember;


    public WmsJsonData() {

    }

    public WmsJsonData(boolean ret) {
        this.isCompression = ret;
    }

    public static WmsJsonData success(Object object, String msg) {
        WmsJsonData jsonData = new WmsJsonData(true);
        jsonData.data = object;
        jsonData.message = msg;
        return jsonData;
    }

    public static WmsJsonData success(Object object) {
        WmsJsonData jsonData = new WmsJsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static WmsJsonData success() {
        WmsJsonData jsonData = new WmsJsonData(true);
        return jsonData;
    }

    public static WmsJsonData fail(String msg) {
        WmsJsonData jsonData = new WmsJsonData(false);
        jsonData.message = msg;
        return jsonData;
    }

    public static WmsJsonData fail(Object object, String msg) {
        WmsJsonData jsonData = new WmsJsonData(false);
        jsonData.message = msg;
        jsonData.data = object;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("isCompression", isCompression);
        result.put("message", message);
        result.put("data", data);
        result.put("stateCode", stateCode);
        result.put("errorMember", errorMember);
        return result;
    }
}
