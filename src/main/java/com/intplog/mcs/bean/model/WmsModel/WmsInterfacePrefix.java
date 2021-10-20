package com.intplog.mcs.bean.model.WmsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/8 17:07
 */
@Getter
@Setter
@ToString
public class WmsInterfacePrefix {

    /**
     * 接口类型（固定值：2）
     */
    private String LoginAuthType = "2";

    /**
     * 用户名（固定值：123）
     */
    private String UserName = "999";

    /**
     * 密码（固定值：c4ca4238a0b923820dcc509a6f75849b）
     */
    private String UserPass = "c4ca4238a0b923820dcc509a6f75849b";

    /**
     * 账套（根据现场数据库设置判断）
     */
    private String AccountCode = "db_jw_wms";

    /**
     * 文件名（固定值：DAL_WCS）
     */
    private String PluginModuleName = "DAL_WCS";

    /**
     * 方法名（固定值：Collection_RFID）
     */
    private String ActionName;

    /**
     * 参数列表
     */
    private ArrayList<Object> ParamList;

    public WmsInterfacePrefix() {
    }

    public WmsInterfacePrefix(String actionName, Object object) {
        ActionName = actionName;
        ParamList=new ArrayList<>();
        ParamList.add(object);
    }
}
