package com.intplog.mcs.common;


import com.intplog.mcs.exception.ParamException;
import com.intplog.mcs.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        String url = request.getRequestURI().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        // .json,.page
        // 项目中所有请求json数据，都用.json结尾
        if(url.endsWith(".json")){
            if(ex instanceof PermissionException || ex instanceof ParamException){
                JsonData result = JsonData.fail(ex.getMessage());
                //保证异常返回结果跟正常返回结果一致，所以toMap();
                mv = new ModelAndView("jsonView",result.toMap());
            }else{
                log.error("unknown json exception,url:" + url,ex );
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("exception",result.toMap());
            }
            //项目中所有请求page页面，都用.page结尾
        }else if(url.endsWith(".page")){
            log.error("unknown page exception,url:" + url,ex );
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",result.toMap());
        }else{
            log.error("unknown exception,url:" + url,ex );
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }

        return null;
    }
}
