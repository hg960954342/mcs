package com.intplog.mcs.bean.viewmodel;


/**
 * @author liaoliming
 * @create 2019-09-06
 */

/**
 * 自定义需要校验的License参数
 */
public class LicenseModel {

    /**
     * 主键
     */
    private String id;

    /**
     * 机器码
     */
    private String keyCode;

    /**
     * 激活码
     */
    private String activeCode;

    /**
     * 激活时间
     */
    private String createDate;

    /**
     * 软件系统类型:RCS还是GCS
     */
    private String type;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 截止日期
     */
    private  String endDate;

    /**
     * 项目名称
     */
    private String pName;


    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    /**
     * 系统类型
     */
    private String osName;

    /**
     * 数量
     */
    private Integer count;


    private String now;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "Authorise{" +
                "id='" + id + '\'' +
                ", keyCode='" + keyCode + '\'' +
                ", activeCode='" + activeCode + '\'' +
                ", createDate='" + createDate + '\'' +
                ", type='" + type + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", pName='" + pName + '\'' +
                ", cpuSerial='" + cpuSerial + '\'' +
                ", mainBoardSerial='" + mainBoardSerial + '\'' +
                ", osName='" + osName + '\'' +
                ", count=" + count +
                ", now='" + now + '\'' +
                '}';
    }
}
