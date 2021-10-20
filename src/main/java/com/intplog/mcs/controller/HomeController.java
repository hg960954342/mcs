package com.intplog.mcs.controller;

import com.intplog.mcs.bean.dto.AclModuleLevelDto;
import com.intplog.mcs.bean.viewmodel.Menu;
import com.intplog.mcs.common.RequestHolder;
import com.intplog.mcs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author JiangZhongXing
 * @create 2019-01-08 9:49 PM
 */
@Controller
public class HomeController {

        @Autowired
        private SysTreeService sysTreeService;
        @Autowired
        private SysMenuService sysMenuService;

        @GetMapping("/main")
        public String main(ModelMap model) {
                model.addAttribute("menus", menuTree(RequestHolder.getCurrentUser().getId()));
                return "home";
        }

        /**
         * 权限管理-动态菜单生成
         *
         * @param userId
         * @return
         */
        public List<Menu> menuTree(int userId) {
                List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.userAclTree(userId);
                return sysMenuService.changeTreeToMenu(aclModuleLevelDtoList);
        }

        /**
         * 控制台页面
         *
         * @return
         */
        @GetMapping("/home")
        public String home(Model model) {
                // model.addAttribute("typeCount", getTypeCount());
                // model.addAttribute("hourShelfCarry", getHourShelfCarry());
                // model.addAttribute("hourPutaway", getHourPutaway());
                // model.addAttribute("hourSoldOut", getHourSoldOut());
                return "home/index";
        }


        /**
         * 首页信息统计
         * @return
         */

}
