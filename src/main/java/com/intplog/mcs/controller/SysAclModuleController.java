package com.intplog.mcs.controller;

import com.intplog.mcs.bean.viewmodel.AclModuleParam;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.SysAclModuleService;
import com.intplog.mcs.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

        @Resource
        private SysAclModuleService sysAclModuleService;
        @Resource
        private SysTreeService sysTreeService;

        @RequestMapping("/acl.page")
        public String page() {
                return "acl/aclList";
        }

        @RequestMapping("/save.json")
        @ResponseBody
        public JsonData saveAclModule(AclModuleParam param) {
                sysAclModuleService.save(param);
                return JsonData.success();
        }

        @RequestMapping("/update.json")
        @ResponseBody
        public JsonData updateAclModule(AclModuleParam param) {
                sysAclModuleService.update(param);
                return JsonData.success();
        }

        @RequestMapping("/tree.json")
        @ResponseBody
        public JsonData tree() {
                return JsonData.success(sysTreeService.aclModuleTree());
        }

        @RequestMapping("/delete.json")
        @ResponseBody
        public JsonData delete(@RequestParam("id") int id) {
                sysAclModuleService.delete(id);
                return JsonData.success();
        }
}
