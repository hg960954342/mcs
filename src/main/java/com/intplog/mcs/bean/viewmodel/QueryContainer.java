package com.intplog.mcs.bean.viewmodel;

/**
 * 查询货架信息请求Json类
 *
 * @author JiangZhongXing
 * @create 2019-01-10 4:25 PM
 */
public class QueryContainer {
    private Integer queryType;
    private String area;

    @Override
    public String toString() {
        return "QueryShelf{" +
                "queryType=" + queryType +
                ", area='" + area + '\'' +
                '}';
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
