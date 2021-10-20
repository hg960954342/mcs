package com.intplog.mcs.bean.viewmodel;

/**
 * 查询区域返回Json类
 *
 * @author JiangZhongXing
 * @create 2019-01-10 3:23 PM
 */
public class Container {
    private String area;
    private Integer shelfId;

    @Override
    public String toString() {
        return "Shelf{" +
                "area='" + area + '\'' +
                ", shelfId=" + shelfId +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }
}
