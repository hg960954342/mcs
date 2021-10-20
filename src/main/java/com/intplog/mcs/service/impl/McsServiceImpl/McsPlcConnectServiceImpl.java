package com.intplog.mcs.service.impl.McsServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.McsModel.McsPlcConnect;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.mapper.McsMapper.McsPlcConnectMapper;
import com.intplog.mcs.service.McsService.McsPlcConnectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2019/10/12 14:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class McsPlcConnectServiceImpl implements McsPlcConnectService {

    @Resource
    private McsPlcConnectMapper mcsPlcConnectMapper;

    @Override
    public List<McsPlcConnect> getExcelExport(String id, String name) {
        return mcsPlcConnectMapper.getList(id, name);
    }

    @Override
    public PageData getAll(String ip, String name, int pageNum, int pageSize) {
        PageData pageData = new PageData();
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<McsPlcConnect> all = mcsPlcConnectMapper.getList(ip, name);
        pageData.setMsg("");
        pageData.setCount(page.getTotal());
        pageData.setCode(0);
        pageData.setData(all);
        return pageData;
    }

    @Override
    public List<McsPlcConnect> getAll() {
        return mcsPlcConnectMapper.getAll();
    }

    @Override
    public McsPlcConnect getMcsPlcConnectById(String id) {
        return mcsPlcConnectMapper.getMcsPlcConnectById(id);
    }

    @Override
    public List<McsPlcConnect> getByFunction(int function) {
        return mcsPlcConnectMapper.getByFunction(function);
    }

    @Override
    public int insertMcsPlcConnect(McsPlcConnect mcsPlcConnect) {
        return mcsPlcConnectMapper.insertMcsPlcConnect(mcsPlcConnect);
    }

    @Override
    public int updateMcsPlcConnect(McsPlcConnect mcsPlcConnect) {
        return mcsPlcConnectMapper.updateMcsPlcConnect(mcsPlcConnect);
    }

    @Override
    public PageData deleteMcsPlcConnectById(String id) {
        PageData pageData = new PageData();
        int i = mcsPlcConnectMapper.deleteMcsPlcConnect(id);

        pageData.setCode(0);
        pageData.setMsg("");
        if (i < 1) {
            pageData.setMsg("更新失败");
        }
        return pageData;
    }

    @Override
    public JsonData importExcel(List<McsPlcConnect> mcsPlcConnectList) {
        List<McsPlcConnect> plcConnectList = mcsPlcConnectMapper.getAll();
        List<Object> idList = plcConnectList.stream().map(x -> x.getId()).collect(Collectors.toList());//数据库已存在编号
        List<String> ipList = plcConnectList.stream().map(x -> x.getIp()).collect(Collectors.toList());//数据库已存在ip

        if (mcsPlcConnectList.size() > 0) {
            for (int i = 0; i < mcsPlcConnectList.size(); i++) {
                McsPlcConnect mcsPlcConnect = mcsPlcConnectList.get(i);
                if (idList.contains(mcsPlcConnect.getId()) || ipList.contains(mcsPlcConnect.getIp())) {
                    return JsonData.fail(MessageFormat.format("导入数据第{0}行编号和IP在导入数据中已存在", i + 1, mcsPlcConnect.id), "3");
                }
                int a = mcsPlcConnectMapper.importList(mcsPlcConnectList);
                if (a > 0) {
                    return JsonData.success(MessageFormat.format("导入成功，受影响行数{0}", mcsPlcConnectList.size()));
                } else {
                    return JsonData.fail("2");
                }
            }
        } else {
            return JsonData.fail("导入数据为空", "3");
        }


        return null;
    }

}
