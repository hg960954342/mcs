package com.intplog.mcs.controller.McsController;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.service.McsService.McsWeightProperTiesService;
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
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-03 15:14
 **/
@Controller
@RequestMapping("/mcsWeightProperties")
public class McsWeightPropertiesController {

    @Autowired
    private McsWeightProperTiesService mcsWeightProperTiesService;

    /**
     * 连接管理界面
     *
     * @return
     */
    @GetMapping("/")
    public String list() {
        return "weightProperTies/list";
    }

    /**
     * 添加界面
     *
     * @return
     */
    @GetMapping("/adds")
    public String toAddLedConnectPage() {

        return "weightProperTies/add";
    }

    /**
     * 编辑界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String toEditMcsWeightPropertiesPage(@PathVariable("id") String id, Model model) {

        McsWeightProperTies mcsWeightProperTies = mcsWeightProperTiesService.getBcrId(id);
        model.addAttribute("weightProperTies", mcsWeightProperTies);
        return "weightProperTies/add";
    }

    /**
     * 保存功能
     *
     * @param mcsWeightProperties
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public String toAdd(McsWeightProperTies mcsWeightProperties) {
        int i = mcsWeightProperTiesService.insert(mcsWeightProperties);
        if (i > 0) {
            return "添加称配置参数成功";
        } else {
            return "添加称配置参数失败";
        }
    }

    /**
     * name or  connectId查询
     *
     * @param name
     * @param connectId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/data")
    @ResponseBody
    public Object selectBcr(String name, String connectId, int page,int limit) {
        String names = name.equals("") ? null : name;
        String connectIds = connectId.equals("") ? null : connectId;
        return mcsWeightProperTiesService.getAll(names, connectIds, page, limit);
    }

    /**
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/selectId")
    public String selectId(String id, String name) {
        mcsWeightProperTiesService.getBcrName(id, name);
        return "bcr";
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Object deleteBcr(@PathVariable("id") String id) {

        return mcsWeightProperTiesService.deleteBcrConnectId(id);
    }


    /**
     * 更新
     *
     * @param info
     * @return
     */
    @PutMapping("/")
    @ResponseBody
    public String updateConnectId(McsWeightProperTies info) {

        McsWeightProperTies mcsWeightProperties = mcsWeightProperTiesService.getBcrId(info.getId());

        String a = mcsWeightProperties.getId();
        String b = info.getId();
        if (a.equals(b) && mcsWeightProperties.getConnectId().equals(info.getConnectId()) &&
                mcsWeightProperties.getName().equals(info.getName()) &&
                mcsWeightProperties.getRemark().equals(info.getRemark())&&
                mcsWeightProperties.getIp().equals(info.getIp())) {
            return "修改数据不能与原始数据相同";
        } else  {
            mcsWeightProperties.setConnectId(info.getConnectId());
            mcsWeightProperties.setName(info.getName());
            mcsWeightProperties.setRemark(info.getRemark());
            mcsWeightProperties.setIp(info.getIp());
            int i = mcsWeightProperTiesService.updateMcsBcr(mcsWeightProperties);
            if (i > 0) {
                return "更新成功";
            }
        }
        return "更新失败";
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, String name, String connectId) {
        String names = name.equals("") ? null : name;
        String ids = connectId.equals("") ? null : connectId;

        List<McsWeightProperTies> mcsWeightProperties = mcsWeightProperTiesService.getBcrName(names, ids);
        ExcelUtils.exportExcel(mcsWeightProperties, "称配置信息", "称配置信息", McsWeightProperTies.class,
                "称配置信息.xls", response);

    }

    @PostMapping("/import")
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        //需要验证
        importParams.setNeedVerfiy(false);

        ExcelImportResult<McsWeightProperTies> result = ExcelImportUtil.importExcelMore(file.getInputStream(), McsWeightProperTies.class,
                importParams);
        List<McsWeightProperTies> mcsWeightProperties = result.getList();

        for (McsWeightProperTies mcsWeightProperTies1 : mcsWeightProperties
        ) {
            //mcsBcrProperties.setId(StringUtil.getUUID32());
            McsWeightProperTies id = mcsWeightProperTiesService.getBcrId(mcsWeightProperTies1.getId());
            if (id != null) {
                return "3";
            } else {
                return "4";
            }

        }
        int i = mcsWeightProperTiesService.batchInsert(mcsWeightProperties);
        if (i > 1) {
            return "1";
        }
        return "2";
    }

}
