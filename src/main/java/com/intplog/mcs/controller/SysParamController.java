package com.intplog.mcs.controller;


import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.bean.viewmodel.MaxCoord;
import com.intplog.mcs.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhongXing
 * @create 2019-01-11 10:36 AM
 */
@Controller
@RequestMapping("/SysParams")
public class SysParamController {

        @Autowired
        private SysParamService sysParamService;

        /**
         * 系统参数首页
         *
         * @return
         */
        @GetMapping("/")
        public String sysParamsPage() {
                return "sysparam/list";
        }

        /**
         * Json请求数据
         *
         * @return
         */
        @GetMapping("/Data")
        @ResponseBody
        public Object toData(String code, String name, int page, int limit) {
                String co = code.equals("") ? null : code;
                String na = name.equals("") ? null : name;
                return sysParamService.getAll(co, na, page, limit);
        }

        /**
         * @return
         */
        @GetMapping("/Adds")
        public String toAddPage() {
                return "sysparam/add";
        }

        /**
         * 编辑页面显示
         *
         * @return
         */
        @GetMapping("/Edit/{code}")
        public String toEditPage(@PathVariable("code") String code, Model model) {
                SysParam sysParam = sysParamService.getSysParamByCode(code);
                model.addAttribute("Info", sysParam);
                return "sysparam/add";
        }

        /**
         * 添加rgv信息
         *
         * @param sysParam
         * @return
         */
        @PostMapping("/")
        @ResponseBody
        public String toAddSysParam(SysParam sysParam) {
                int i = sysParamService.insertSysParam(sysParam);
                if (i > 0) {
                        return "添加系统参数成功！";
                } else {
                        return "添加系统参数失败！";
                }
        }

        /**
         * 修改RGV信息
         *
         * @param sysParam
         * @return
         */
        @PutMapping("/")
        @ResponseBody
        public String toEditSysParam(SysParam sysParam) {
                SysParam info = sysParamService.getSysParamByCode(sysParam.getCode());
                if (info != null) {
                        int i = sysParamService.updateSysParam(sysParam);
                        if (i > 0) {
                                return "修改系统参数成功！";
                        }
                }
                return "修改系统参数失败！";
        }

        /**
         * 删除数据
         *
         * @param code
         * @return
         */
        @DeleteMapping("/{code}")
        @ResponseBody
        public Object toDeleteSysParam(@PathVariable("code") String code) {
                return sysParamService.deleteSysParamBymId(code);
        }

        /**
         * 获取地图参数
         */
        @PostMapping("/MapParams")
        @ResponseBody
        public Object toMapParams() {
                SysParam xCoord = sysParamService.getSysParamByCode("XCoordMax");
                SysParam yCoord = sysParamService.getSysParamByCode("YCoordMax");
                MaxCoord maxCoord = new MaxCoord();
                maxCoord.setX(xCoord.getValue());
                maxCoord.setY(yCoord.getValue());
                return maxCoord;
        }

}