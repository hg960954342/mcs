package com.intplog.mcs.controller.McsController;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.intplog.mcs.bean.model.McsModel.McsPlcConnect;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.McsService.McsPlcConnectService;
import com.intplog.mcs.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/12 15:36
 */
@Controller
@RequestMapping("/mcsPlcConnect")
public class McsPlcConnectController {

    @Autowired
    public McsPlcConnectService mcsPlcConnectService;

    /**
     * 系统list页面
     *
     * @return
     */
    @GetMapping("/")
    public String toData() {
        return "mcsplcconnect/list";
    }

    /**
     * Json请求数据
     *
     * @param ip
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/Data")
    @ResponseBody
    public Object toData(String ip, String name, int page, int limit) {
        String i = ip.equals("") ? null : ip;
        String na = name.equals("") ? null : name;
        return mcsPlcConnectService.getAll(i, na, page, limit);
    }

    /**
     * 添加页面
     *
     * @return
     */
    @GetMapping("/adds")
    public String toAddMcsPlcConnectPage() {
        return "mcsplcconnect/add";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String toEditMcsPlcConnectPage(@PathVariable("id") String id, Model model) {
        McsPlcConnect mcsPlcConnect = mcsPlcConnectService.getMcsPlcConnectById(id);
        model.addAttribute("mcsPlcConnectInfo", mcsPlcConnect);
        return "mcsplcconnect/add";
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
        String plcConnectIp = ip.equals("") ? null : ip;
        String plcConnectName = name.equals("") ? null : name;
        List<McsPlcConnect> getList = mcsPlcConnectService.getExcelExport(plcConnectIp, plcConnectName);
        ExcelUtils.exportExcel(getList, "PLC连接管理", "PLC连接管理", McsPlcConnect.class, "PLC连接管理.xls", httpServletResponse);
    }

    /**
     * 导入excel
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
        ExcelImportResult<McsPlcConnect> result = ExcelImportUtil.importExcelMore(file.getInputStream(), McsPlcConnect.class, importParams);
        List<McsPlcConnect> mcsPlcConnectList = result.getList();
        return mcsPlcConnectService.importExcel(mcsPlcConnectList);
    }

    /**
     * 添加McsPlcConnect信息
     *
     * @param mcsPlcConnect
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public String toAddMcsPlcConnect(McsPlcConnect mcsPlcConnect) {
        System.out.printf(mcsPlcConnect.toString());
        int i = mcsPlcConnectService.insertMcsPlcConnect(mcsPlcConnect);
        if (i > 0) {
            return "添加信息成功";
        } else {
            return "添加信息失败";
        }
    }

    /**
     * 修改McsPlcConnect信息
     *
     * @param info
     * @return
     */
    @PutMapping("/")
    @ResponseBody
    public String toEditMcsPlcConnect(McsPlcConnect info) {
        McsPlcConnect mcsPlcConnect = mcsPlcConnectService.getMcsPlcConnectById(info.getId());
        if (mcsPlcConnect != null) {
            mcsPlcConnect.setIp(info.getIp());
            mcsPlcConnect.setPlcName(info.getPlcName());
            mcsPlcConnect.setType(info.getType());

            int i = mcsPlcConnectService.updateMcsPlcConnect(mcsPlcConnect);
            if (i > 0) {
                return "修改McsPlcConnect成功";
            }
        }
        return "修改McsPlcConnect失败";
    }


    /**
     * 删除McsPlcConnect信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Object toDeleteMcsPlcConnect(@PathVariable("id") String id) {

            return mcsPlcConnectService.deleteMcsPlcConnectById(id);
    }

        /**
         * 获取PLC连接信息
          * @return
         */
    @PostMapping("/getPlcConnect")
    @ResponseBody
   public JsonData getPlcConnect(){
            String message= "";
            boolean isOk;
            Date begin= new Date();
            JsonData jsonData= new JsonData();
            List<McsPlcConnect>plcConnectList= mcsPlcConnectService.getAll();
            if(plcConnectList.size()>0){
                    isOk=true;
            }else {
                    message+="获取PLC连接信息失败";
            }
            isOk = message.equals("")?true:false;
            jsonData.setSuccess(isOk);
            jsonData.setMessage(message);
            jsonData.setData(plcConnectList);
            return  jsonData;

   }

}
