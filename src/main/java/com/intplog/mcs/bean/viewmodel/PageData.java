package com.intplog.mcs.bean.viewmodel;

/**
 * @author JiangZhongXing
 * @create 2019-01-10 11:02 PM
 */
public class PageData {
    private Integer code;
    private String msg;
    private long count;
    private Object data;

    @Override
    public String toString() {
        return "PageData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
