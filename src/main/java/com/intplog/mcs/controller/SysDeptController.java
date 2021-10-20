package com.intplog.mcs.controller;


import com.intplog.mcs.bean.dto.DeptLevelDto;
import com.intplog.mcs.bean.viewmodel.DeptParam;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.SysDeptService;
import com.intplog.mcs.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-09-10
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

        @Resource
        private SysDeptService sysDeptService;

        @Resource
        private SysTreeService sysTreeService;

        @RequestMapping("/dept.page")
        public String page() {
                return "dept/deptList";
        }

        @RequestMapping("/save.json")
        @ResponseBody
        public JsonData saveDept(DeptParam param) {

                sysDeptService.save(param);
                return JsonData.success();
        }

        @RequestMapping("/tree.json")
        @ResponseBody
        public JsonData tree() {
                List<DeptLevelDto> dtoList = sysTreeService.deptTree();
                return JsonData.success(dtoList);
        }

        @RequestMapping("/update.json")
        @ResponseBody
        public JsonData updateDept(DeptParam param) {

                sysDeptService.update(param);
                return JsonData.success();
        }

        @RequestMapping("/delete.json")
        @ResponseBody
        public JsonData delete(@RequestParam("id") int id) {
                sysDeptService.delete(id);
                return JsonData.success();
        }
}
