package com.intplog.mcs.bean.viewmodel;

/**
 * @author JiangZhongXing
 * @create 2019-03-21 10:33
 */
public class MaxCoord {
    private String x;
    private String y;

    @Override
    public String toString() {
        return "MaxCoord{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
