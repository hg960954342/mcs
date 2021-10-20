package com.intplog.mcs.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.intplog.mcs.bean.model.LedConnect;
import com.intplog.mcs.service.LedConnectService;
import com.intplog.mcs.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-11 11:05
 */
@Controller
@RequestMapping("/ledConnect")
@Slf4j
public class LedConnectController {

    @Autowired
    private LedConnectService ledConnectService;

    /**
     * 系统list页面
     */
    @GetMapping("/")
    public String toData(){

        return "ledconnect/list";
    }

        /**
         * Json请求数据
         *
         * @return
         */
    @GetMapping("/Data")
    @ResponseBody
    public Object toData(String id, String name, int page, int limit) {
        String i = id.equals("")?null:id;
        String na = name.equals("")?null:name;
        return ledConnectService.getAll(i, na, page, limit);
    }

    /**
     * 添加页面
     *
     * @return
     */
    @GetMapping("/adds")
    public String toAddLedConnectPage() {
        return "ledconnect/add";
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String toEditLedConnectPage(@PathVariable("id") String id, Model model){

        LedConnect ledConnect = ledConnectService.getLedConnectById(id);
        model.addAttribute("ledConnectInfo",ledConnect);
        return "ledconnect/add";
    }

    /**
     * 添加ledConnection信息
     *
     * @param ledConnect
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public String toAddLedConnect(LedConnect ledConnect) {
        System.out.printf(ledConnect.toString());

        int i = ledConnectService.insertLedConnect(ledConnect);
        if (i > 0){
            return "添加信息成功！";
        } else {
            return "添加信息失败！";
        }
    }

    /**
     * 修改LED连接信息
     *
     * @param info
     * @return
     */
    @PutMapping("/")
    @ResponseBody
    public String toEditConnect(LedConnect info) {
        LedConnect ledConnect = ledConnectService.getLedConnectById(info.getId());
        if (ledConnect != null) {
            ledConnect.setIp(info.getIp());
            ledConnect.setName(info.getName());
            ledConnect.setPort(info.getPort());
            ledConnect.setRemark(info.getRemark());
            int i = ledConnectService.updateLedConnect(ledConnect);
            if (i > 0){
                return "修改LedConnect信息成功！";
            }
        }
        return "修改LedConnect信息失败！";
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Object toDeleteLedConnect(@PathVariable("id") String id){

        return ledConnectService.deleteLedConnectById(id);
    }

    /**
     * excel导出
     * @param response
     * @param id
     * @param name
     */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response,String id, String name){
        String i = id.equals("")?null:id;
        String na = name.equals("")?null:name;
        List<LedConnect> ledConnectList = ledConnectService.getExcelExport(i, na);
        ExcelUtils.exportExcel(ledConnectList, "LED连接信息", "LED连接信息", LedConnect.class, "LED连接信息.xls", response);
    }

    /**
     * excel导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile file,HttpServletResponse resp) throws Exception{
        //设置导入的参数
        ImportParams params = new ImportParams();
        //去掉第一行表头
        params.setHeadRows(1);
        params.setTitleRows(1);
        //开启验证
        params.setNeedVerfiy(true);
        //加入自定义验证
//        params.setVerifyHandler(new ExcelImportVerifyHandler());
        ExcelImportResult<LedConnect> result = ExcelImportUtil.importExcelMore(file.getInputStream(), LedConnect.class,
                    params);
        List<LedConnect> ledConnectList = result.getList();
        //控制台输出导入数据
        ledConnectList.forEach((list)-> System.out.println(list));
        int i = ledConnectService.batchInsert(ledConnectList);
        if(i>0){
            //1表示导入成功，2表示导入失败
            log.info("从Excel导入数据一共 {} 行 ", ledConnectList.size());
            return "1";
        } else {
            return "2";
        }

    }

}
