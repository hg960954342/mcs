package com.intplog.mcs.controller;


import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.bean.viewmodel.LicenseModel;
import com.intplog.mcs.service.SysParamService;
import com.intplog.mcs.utils.LicenseUtils;
import com.intplog.ractools.utils.LicenseMakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.intplog.ractools.utils.LicenseMakeUtils.sysDate;

/**
 * 软件注册授权
 */
@Slf4j
@Controller
@RequestMapping("/license")
public class LicenseController {

        @Autowired
        SysParamService sysParamService;

        /**
         * 首页
         */
        @GetMapping("/")
        public String toLicensePage(Model model) {

                LicenseMakeUtils.key = "fhuehsucnhfli!@#isdn123~~+_{jfi321";
                LicenseMakeUtils.beginLength = 20;
                LicenseMakeUtils.endLength = 30;

                String code = getLicenseCode();
                LicenseModel license = check(code);

                //验证激活码是否有效
                boolean checkCode = license != null;
                //生成激活码
                if (checkCode) {
                        model.addAttribute("projectName", license.getpName());
                        model.addAttribute("beginDate", license.getBeginDate());
                        model.addAttribute("endDate", license.getEndDate());
                        model.addAttribute("count", license.getCount());
                        return "license/info";
                }
                //获取项目名称
                model.addAttribute("projectName", getProjectName());
                model.addAttribute("activeCode", createCode());
                model.addAttribute("sysDate", sysDate);
                return "license/active";
        }

        /**
         * 验证系统是否超期
         *
         * @param value
         * @return
         */
        public LicenseModel check(String value) {
                boolean check = false;
                LicenseModel model = null;
                if (value != null && !value.isEmpty()) {
                        model = LicenseMakeUtils.decryptEntity(LicenseModel.class, value);
                        check = LicenseUtils.check(model);
                }
                return check ? model : null;
        }

        /**
         * 获取License对应的值
         */
        private String getLicenseCode() {
                String value = "";
                SysParam sysParam = sysParamService.getSysParamByCode("License");
                if (sysParam != null && !sysParam.getValue().isEmpty()) {
                        value = sysParam.getValue();
                } else if (sysParam == null) {
                        SysParam sp = new SysParam();
                        sp.setName("授权文件");
                        sp.setCode("License");
                        sp.setValue(value);
                        sp.setSort(sysParamService.getTotal() + 1);
                        sysParamService.insertSysParam(sp);
                }
                return value;
        }


        /**
         * 获取项目名称
         *
         * @return
         */
        private String getProjectName() {
                //获取项目名称
                SysParam sysParam = sysParamService.getSysParamByCode("ProjectName");
                String pName = sysParam.getValue();
                return pName;
        }

        /**
         * 生成机器码
         *
         * @return
         */
        private String createCode() {

                /**
                 * 时间格式化
                 */
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Random random = new Random();

                LicenseModel licmodel = new LicenseModel();
                licmodel.setCpuSerial(LicenseMakeUtils.cpu);
                licmodel.setType("RCS");
                licmodel.setOsName(LicenseMakeUtils.osName);
                //设置AGV数量
                licmodel.setCount(20);
                //保证每次生成的机器码都不一样
                licmodel.setNow(sdf.format(new Date()) + "," + random.nextInt(100));
                //获取项目名称
                licmodel.setpName(getProjectName());
                licmodel.setBeginDate(sysDate);

                //生成机器码 AES
                String activeCode = LicenseMakeUtils.encryptEntity(licmodel);
                return activeCode;
        }

        /**
         * 激活
         */
        @PostMapping("/active")
        @ResponseBody
        public String toActive(String key) {
                LicenseModel lModel = check(key);
                if (lModel != null) {
                        sysParamService.updateSysParamValue("License", key);
                        return "ok";
                }
                return "err";
        }
}
