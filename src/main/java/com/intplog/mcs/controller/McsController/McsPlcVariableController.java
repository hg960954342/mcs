package com.intplog.mcs.controller.McsController;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.intplog.mcs.bean.model.McsModel.McsPlcVariable;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.McsService.McsPlcVariableService;
import com.intplog.mcs.utils.ExcelUtils;
import com.intplog.mcs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/15 14:21
 */
@Controller
@RequestMapping("/mcsPlcVariable")
public class McsPlcVariableController {

        @Autowired
        public McsPlcVariableService mcsPlcVariableService;

        /**
         * 系统list页面
         *
         * @return
         */
        @GetMapping("/")
        public String toData() {
                return "mcsPlcVariable/list";
        }

        /**
         * Json请求数据
         *
         * @param page
         * @param limit
         * @return
         */
        @GetMapping("/Data")
        @ResponseBody
        public Object toData(String name, String groupNumber, int page, int limit) {
                String _name = name.equals("") ? null : name;
                String _group = groupNumber.equals("") ? null : groupNumber;
                return mcsPlcVariableService.getAll(_group, _name, page, limit);
        }

        /**
         * 添加页面
         *
         * @return
         */
        @GetMapping("/adds")
        public String toAddMcsPlcVariablePage() {
                return "mcsPlcVariable/add";
        }

        /**
         * 编辑页面
         *
         * @param id
         * @param model
         * @return
         */
        @GetMapping("/edit/{id}")
        public String toEditMcsPlcVariablePage(@PathVariable("id") String id, Model model) {
                McsPlcVariable mcsPlcVariable = mcsPlcVariableService.getMcsPlcVariableById(id);
                model.addAttribute("mcsPlcVariableInfo", mcsPlcVariable);
                return "mcsPlcVariable/add";
        }

        /**
         * 导出excel
         *
         * @param httpServletResponse
         * @param ip
         * @param name
         */
        @GetMapping("/export")
        public void exportExcel(HttpServletResponse httpServletResponse, String ip, String name) {
                String _ip = ip.equals("") ? null : ip;
                String _name = name.equals("") ? null : name;
                List<McsPlcVariable> mcsPlcVariableList = mcsPlcVariableService.getExcelExport(_ip, _name);
                ExcelUtils.exportExcel(mcsPlcVariableList, "PLC配置管理", "PLC配置管理", McsPlcVariable.class, "PLC配置管理.xls", httpServletResponse);
        }

        /**
         * 导入excel
         *
         * @param file
         * @return
         * @throws Exception
         */
        @PostMapping("/import")
        @ResponseBody
        public JsonData importExcel(@RequestParam("file") MultipartFile file) throws Exception {
                ImportParams importParams = new ImportParams();
                //数据处理
                importParams.setHeadRows(1);
                importParams.setTitleRows(1);
                importParams.setNeedVerfiy(true);
                ExcelImportResult<McsPlcVariable> result = ExcelImportUtil.importExcelMore(file.getInputStream(), McsPlcVariable.class, importParams);
                List<McsPlcVariable> mcsPlcVariableList = result.getList();
                return mcsPlcVariableService.importExcel(mcsPlcVariableList);
        }

        /**
         * 添加mcsPlcVariable信息
         *
         * @param mcsPlcVariable
         * @return
         */
        @PostMapping("/")
        @ResponseBody
        public String toAddMcsPlcVariable(McsPlcVariable mcsPlcVariable) {
                mcsPlcVariable.setId(StringUtil.getUUID32());
                int i = mcsPlcVariableService.insertMcsPlcVariable(mcsPlcVariable);
                if (i > 0) {
                        return "添加信息成功";
                } else {
                        return "添加信息失败";
                }
        }

        /**
         * 修改mcsPlcVariable信息
         *
         * @param mcsPlcVariable
         * @return
         */
        @PutMapping("/")
        @ResponseBody
        public String toEditMcsPlcVariable(McsPlcVariable mcsPlcVariable) {
                McsPlcVariable mcsPlcVariable1 = mcsPlcVariableService.getMcsPlcVariableById(mcsPlcVariable.getId());
                if (mcsPlcVariable1 != null) {
                        int i = mcsPlcVariableService.updateMcsPlcVariable(mcsPlcVariable);
                        if (i > 0) {
                                return "修改McsPlcVariable成功";
                        }
                }
                return "修改McsPlcVariable失败";
        }

        /**
         * 删除mcsPlcVariable信息
         *
         * @param id
         * @return
         */
        @DeleteMapping("/{id}")
        @ResponseBody
        public Object toDeleteMcsPlcVariable(@PathVariable("id") String id) {

                return mcsPlcVariableService.deleteMcsPlcVariableById(id);
        }

        /**
         * 获取PLC交互地址
         * @return
         */
        @PostMapping("/getPlcVariableParam")
        @ResponseBody
        public JsonData getMcsPlcVariableParam() {
                boolean isOk;
                String message = "";
                JsonData jsonData = new JsonData();
                List<McsPlcVariable> mcsPlcVariableList = mcsPlcVariableService.getAll();
                if (mcsPlcVariableList.size() > 0) {
                        isOk = true;
                } else {
                        message += "获取PLC交互地址失败";
                }
                isOk = message.equals("") ? true : false;
                jsonData.setSuccess(isOk);
                jsonData.setMessage(message);
                jsonData.setData(mcsPlcVariableList);
                return jsonData;
        }

}
