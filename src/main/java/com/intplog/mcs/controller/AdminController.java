package com.intplog.mcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liaoliming
 * @create 2019-09-010
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

        @RequestMapping("index")
        public String index() {
                return "admin";
        }
}
