package com.intplog.mcs.service.impl;


import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.service.SysParamService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * @author jiangzhongxing
 * @Date 2019-12-23 19:29
 */
@Service
public class InitDataListener implements InitializingBean, ServletContextAware {

    /**
     * WCS请求地址
     */
    public static String EIS_URL = "";

    /**
     * SMS请求地址
     */
    public static String SMS_URL = "";

    /**
     * WMS请求地址
     */
    public static String WMS_URL = "";

    @Resource
    private SysParamService sysParamService;


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        SysParam eis_url = sysParamService.getSysParamByCode("EIS_URL");
        this.EIS_URL = eis_url.getValue();

        SysParam sms_url = sysParamService.getSysParamByCode("SMS_URL");
        this.SMS_URL = sms_url.getValue();

        SysParam wms_url = sysParamService.getSysParamByCode("WMS_URL");
        this.WMS_URL = wms_url.getValue();
    }
}