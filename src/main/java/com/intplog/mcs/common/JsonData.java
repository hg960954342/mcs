package com.intplog.mcs.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
    http请求的封装
    ret:返回结果，true或者false
    msg:返回异常通知
    data:返回正常数据
 */

@Data
public class JsonData implements Serializable {

    private boolean success;

    private String message;

    private String code;

    private Object data;

    public JsonData(boolean success, String message, String code,Object object) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = object;
    }

    public JsonData(boolean success, String message, String code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public JsonData() {

    }

    public JsonData(boolean ret) {
        this.success = ret;
    }

    public static JsonData success(Object object, String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.message = msg;
        return jsonData;
    }

    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success() {
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.message = msg;
        return jsonData;
    }

    public static JsonData fail(Object object, String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.message = msg;
        jsonData.data = object;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("message", message);
        result.put("data", data);
        return result;
    }
}
