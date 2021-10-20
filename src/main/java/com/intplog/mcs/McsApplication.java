package com.intplog.mcs;

import com.intplog.mcs.common.HttpInterceptor;
import com.intplog.mcs.filter.AclControlFilter;
import com.intplog.mcs.filter.LoginFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author liaoliming
 * @Date 2019-08-22 12:17
 */

@MapperScan(basePackages = {"com.intplog.mcs.mapper"})
@SpringBootApplication
public class McsApplication extends WebMvcConfigurerAdapter  {

    public static void main(String[] args) {
        SpringApplication.run(McsApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/sys/*", "/admin/*","/main.html","/home","/license/*",
                "/SysParams/*","/mcsBcrProperties/*","/mcsAlarmLog/*","/mcsBcrConnect/*",
                "/mcsInterfaceLog/*","/mcsLog/*","/mcsPlcConnect/*","/mcsPlcVariable/*",
                "/mcsTaskLog/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AclControlFilter());
        registration.addUrlPatterns("/sys/*", "/admin/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
    }

}
