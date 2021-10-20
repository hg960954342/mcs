package com.intplog.mcs.filter;


import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.common.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author liaoliming
 * @Date 2019-09-11
 */
@Slf4j
public class LoginFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {


        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) servletRequest;
                HttpServletResponse resp = (HttpServletResponse) servletResponse;

                SysUser sysUser = (SysUser) req.getSession().getAttribute("user");

                //如果没有登录,跳转到登录页面
                if (sysUser == null) {
                        resp.sendRedirect("/");
                        return;
                }
                //如果登录了，把相关登录信息放在ThreadHolder里面
                RequestHolder.add(sysUser);
                RequestHolder.add(req);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
        }

        @Override
        public void destroy() {

        }

}
