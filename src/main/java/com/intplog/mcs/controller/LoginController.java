package com.intplog.mcs.controller;

import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.bean.model.SysUser;
import com.intplog.mcs.bean.viewmodel.LogOut;
import com.intplog.mcs.service.SysParamService;
import com.intplog.mcs.service.SysUserService;
import com.intplog.mcs.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author JiangZhongXing
 * @create 2018-12-25 6:51 PM
 */
@Controller
public class LoginController {

        @Autowired
        private SysUserService sysUserService;

        @Autowired
        private SysParamService sysParamService;

        @PostMapping(value = "/user/login")
        public String login(@RequestParam("username") String userName,
                            @RequestParam("password") String password,
                            Map<String, Object> map, HttpSession session) {
                String errorMsg = "用户名或密码不可以为空！";

                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
                        SysUser user = sysUserService.findByKeyword(userName);
                        String pwd = Md5Utils.MD5Encode(password);

                        if (user == null) {
                                errorMsg = "用户名或密码不正确！";
                        } else if (user.getStatus() != 1) {
                                errorMsg = "用户已被冻结，请联系管理员";
                        } else if (user.getPassword().equals(pwd)) {
                                //登陆成功，防止表单重复提交，可以重定向到主页
                                List<SysParam> keyAndValue = sysParamService.getKeyAndValue();
                                for (SysParam pms : keyAndValue) {
                                        session.setAttribute(pms.getCode().trim(), pms.getValue().trim());
                                }
                                session.setAttribute("user", user);
                                errorMsg = "";
                                return "redirect:/main.html";
                        } else {
                                errorMsg = "用户名或密码不正确！";
                        }
                }

                //登陆失败
                map.put("msg", errorMsg);
                return "login";
        }

        /**
         * 注销
         *
         * @param session
         * @return
         */
        @GetMapping("/user/logout")
        @ResponseBody
        public LogOut logOut(HttpSession session) {
                session.invalidate();
                LogOut logOut = new LogOut();
                logOut.setCode(0);
                logOut.setMsg("退出成功");
                logOut.setData(null);
                return logOut;
        }
}
