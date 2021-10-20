package com.intplog.mcs.bean.viewmodel;

/**
 * 退出消息类
 *
 * @author JiangZhongXing
 * @create 2019-01-08 9:58 PM
 */
public class LogOut {

    private Integer code;

    private String msg;

    private Object data;

    @Override
    public String toString() {
        return "LogOut{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
