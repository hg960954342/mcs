package com.intplog.mcs.controller;

import com.google.common.collect.Maps;
import com.intplog.mcs.bean.dto.AclModuleLevelDto;
import com.intplog.mcs.bean.viewmodel.PageQuery;
import com.intplog.mcs.bean.viewmodel.PageResult;
import com.intplog.mcs.bean.viewmodel.UserParam;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.common.RequestHolder;
import com.intplog.mcs.service.SysMenuService;
import com.intplog.mcs.service.SysRoleService;
import com.intplog.mcs.service.SysTreeService;
import com.intplog.mcs.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liaoliming
 * @Date 2019-09-06
 */

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

        @Resource
        private SysUserService sysUserService;
        @Resource
        private SysTreeService sysTreeService;
        @Resource
        private SysRoleService sysRoleService;
        @Resource
        private SysMenuService sysMenuService;

        @RequestMapping("/noAuth.page")
        public String noAuth() {
                return "noAuth/noAuth.html";
        }

        @RequestMapping("/save.json")
        @ResponseBody
        public JsonData saveUser(UserParam param) {
                sysUserService.save(param);
                return JsonData.success();
        }

        @RequestMapping("/update.json")
        @ResponseBody
        public JsonData updateUser(UserParam param) {
                sysUserService.update(param);
                return JsonData.success();
        }

        @RequestMapping("page.json")
        @ResponseBody
        public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
                PageResult result = sysUserService.getPageByDeptId(deptId, pageQuery);
                return JsonData.success(result);
        }

        @RequestMapping("/acls.json")
        @ResponseBody
        public JsonData acls(@RequestParam("userId") int userId) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("acls", sysTreeService.userAclTree(userId));
                map.put("roles", sysRoleService.getRoleListByUserId(userId));
                return JsonData.success(map);
        }

        @RequestMapping("/menu.json")
        @ResponseBody
        public JsonData menuTree() {
                List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.userAclTree(RequestHolder.getCurrentUser().getId());
                return JsonData.success(sysMenuService.changeTreeToMenu(aclModuleLevelDtoList));
        }
}
