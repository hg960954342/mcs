package com.intplog.mcs.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.intplog.mcs.bean.model.LedDisplay;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.LedDisplayService;
import com.intplog.mcs.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @program: wcs
 * @description
 * @author: Tianlei
 * @create: 2019-10-10 10:06
 **/
@Controller
@RequestMapping("/ledDisplay")
@Slf4j
public class LedDisplayController {
        @Autowired
        private LedDisplayService ledDisplayService;

        /**
         * list页面
         *
         * @return
         */
        @GetMapping("/")
        private Object todData() {
                return "leddisplay/list";
        }

        @GetMapping("/add")
        private Object toLedDisplayAdds() {
                return "leddisplay/add";
        }

        /**
         * 添加Led显示数据
         *
         * @param ledDisplay
         * @return
         */
        @RequestMapping("/adds")
        @ResponseBody
        public String toAddLedDisplay(LedDisplay ledDisplay) {
                int i = ledDisplayService.insertLedDisplay(ledDisplay);
                if (i > 0) {
                        return "添加Led显示数据成功";
                } else {
                        return "添加Led显示数据失败";
                }
        }

        @PostMapping("/import")
        @ResponseBody
        public JsonData importExcel(@RequestParam("file") MultipartFile file) throws Exception {
                ImportParams importParams = new ImportParams();
                // 数据处理
                importParams.setHeadRows(1);
                importParams.setTitleRows(1);
                //需要验证
                importParams.setNeedVerfiy(true);
                ExcelImportResult<LedDisplay> result = ExcelImportUtil.importExcelMore(file.getInputStream(), LedDisplay.class,importParams);
                List<LedDisplay> ledDisplayList = result.getList();
                return  ledDisplayService.insertImport(ledDisplayList);
        }

        @GetMapping("edit/{id}")
        public String toEditLedDisplay(@PathVariable("id") String id, Model model) {
                LedDisplay ledDisplay = ledDisplayService.getLedDisplayById(id);
                model.addAttribute("ledDisplayInfo", ledDisplay);
                return "leddisplay/add";
        }

        @GetMapping("export")
        public void exportExcel(HttpServletResponse httpServletResponse, String id, Integer status) {
                String ledDisplayId = id.equals("") ? null : id;
                List<LedDisplay> ledDisplays = ledDisplayService.getAllList(id, status);
                ExcelUtils.exportExcel(ledDisplays, "LED显示信息", "LED显示信息", LedDisplay.class, "LED显示信息.xls", httpServletResponse);
        }

        /**
         * 更新Led显示数据
         *
         * @return
         */
        @PutMapping("/")
        @ResponseBody
        public String updateLedDisplay(LedDisplay ledDisplay) {
                Date date = Date.from(Instant.now());
                LedDisplay ledDisplay1 = ledDisplayService.getLedDisplayById(ledDisplay.getId());
                if (ledDisplay != null) {
                        ledDisplay1.setContent(ledDisplay.getContent());
                        ledDisplay1.setCreateTime(ledDisplay.getCreateTime());
                        ledDisplay1.setDisplayFormat(ledDisplay.getDisplayFormat());
                        ledDisplay1.setStatus(ledDisplay.getStatus());
                        ledDisplay1.setUpdateTime(date);
                        int i = ledDisplayService.updateLedDisplay(ledDisplay1);
                        if (i > 0) {
                                return "更新数据成功";
                        } else {
                                return "更新数据失败";
                        }
                }
                return "更新数据失败";
        }

        @DeleteMapping("/{id}")
        @ResponseBody
        public Object deleteLedDisplayById(@PathVariable("id") String id) {
                return ledDisplayService.deleteLedDisplayById(id);
        }

        @DeleteMapping("/delete/{day}")
        @ResponseBody
        public String deleteLedDisplay(@PathVariable("day") int day) {
                int i = ledDisplayService.deleteLedDisplay(day);
                if (i > 0) {
                        return "删除数据成功";
                } else {
                        return "删除数据失败";
                }
        }


        @GetMapping("/data")
        @ResponseBody
        public Object getList(String id, Integer status, int page,
                              int limit) {
                String i = id.equals("") ? null : id;
//                Integer s= status.equals("")?0:status;

                return ledDisplayService.getAll(i, status, page, limit);
        }


}
