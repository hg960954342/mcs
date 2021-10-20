package com.intplog.mcs.bean.viewmodel;

/**
 * 批量添加请求
 *
 * @author JiangZhongXing
 * @create 2019-01-19 8:50 PM
 */
public class AddBatch {
    private String area;
    Integer xBegin;
    Integer xEnd;
    Integer yBegin;
    Integer yEnd;

    @Override
    public String toString() {
        return "AddBatch{" +
                "area='" + area + '\'' +
                ", xBegin=" + xBegin +
                ", xEnd=" + xEnd +
                ", yBegin=" + yBegin +
                ", yEnd=" + yEnd +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getxBegin() {
        return xBegin;
    }

    public void setxBegin(Integer xBegin) {
        this.xBegin = xBegin;
    }

    public Integer getxEnd() {
        return xEnd;
    }

    public void setxEnd(Integer xEnd) {
        this.xEnd = xEnd;
    }

    public Integer getyBegin() {
        return yBegin;
    }

    public void setyBegin(Integer yBegin) {
        this.yBegin = yBegin;
    }

    public Integer getyEnd() {
        return yEnd;
    }

    public void setyEnd(Integer yEnd) {
        this.yEnd = yEnd;
    }
}
