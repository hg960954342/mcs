package com.intplog.mcs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.LedDisplay;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.mapper.LedDisplayMapper;
import com.intplog.mcs.service.LedDisplayService;
import com.intplog.mcs.utils.DateHelpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: wcs
 * @description
 * @author: tianlei
 * @create: 2019-10-09 17:41
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class LedDisplayServiceImpl implements LedDisplayService {
        @Resource
        private LedDisplayMapper ledDisplayMapper;

        @Override
        public int insertLedDisplay(LedDisplay ledDisplay) {
                ledDisplay.setCreateTime(new Date());
                ledDisplay.setUpdateTime(new Date());
                return ledDisplayMapper.insertLedDisplay(ledDisplay);
        }

        @Override
        public int updateLedDisplayById(String id, Date date) {
                return ledDisplayMapper.updateLedDisplayById(id, date);
        }

        @Override
        public int updateLedDisplay(LedDisplay ledDisplay) {
                ledDisplay.setUpdateTime(new Date());
                return ledDisplayMapper.updateLedDisPlay(ledDisplay);
        }

        @Override
        public int deleteLedDisplay(int day) {
                Date dt = DateHelpUtil.getDate(day);
                return ledDisplayMapper.deleteLedDisplayLog(dt);
        }

        @Override
        public PageData deleteLedDisplayById(String id) {
                PageData pageData = new PageData();
                int i = ledDisplayMapper.deleteLedDisplayById(id);
                pageData.setCode(0);
                if (i < 1) {
                        pageData.setMsg("删除数据失败！");
                } else {
                        pageData.setMsg("删除数据成功！");
                }
                return pageData;
        }

        @Override
        public PageData getAll(String id, Integer status, int pageNum, int pageSize) {
                PageData pd = new PageData();
                Page<Object> page = PageHelper.startPage(pageNum, pageSize);
                List<LedDisplay> all = ledDisplayMapper.getList(id, status);
                pd.setMsg("");
                pd.setCount(page.getTotal());
                pd.setCode(0);
                pd.setData(all);
                return pd;
        }

        @Override
        public LedDisplay getLedDisplayById(String id) {
                return ledDisplayMapper.selectByPrimaryId(id);
        }

        @Override
        public List<LedDisplay> getAll() {
                return ledDisplayMapper.getAllList();
        }

        @Override
        public List<LedDisplay> getAllList(String id, Integer status) {
                return ledDisplayMapper.getList(id, status);
        }

        @Override
        public JsonData insertImport(List<LedDisplay> ledDisplayList) {
                List<LedDisplay> ledDisplays = new ArrayList<LedDisplay>();
                ledDisplays = ledDisplayMapper.getAllList();
                List<String> ledDisplayById = ledDisplays.stream().map(x -> x.getId()).collect(Collectors.toList());//数据库已存在主键
                List<String> importById=new ArrayList<>();//导入数据编号
                if (ledDisplayList.size() > 0) {
                        for (int m = 0; m <ledDisplayList.size() ; m++) {
                                LedDisplay display= ledDisplayList.get(m);
                                if (importById.contains(display.getId())){
                                        return JsonData.fail(MessageFormat.format("导入数据第{0}行数据编号在导入数据中已存在", m+1), "3");
                                }else {
                                        importById.add(display.getId());
                                }
                        }
                        for (int a = 0; a < ledDisplayList.size(); a++) {
                                LedDisplay ledDisplay = ledDisplayList.get(a);
                                //判断导入数据编号是否与数据库编号重复
                                if (ledDisplayById.contains(ledDisplay.getId())) {
                                        return JsonData.fail(MessageFormat.format("导入失败!,第{0}行数据编号已存在", a+1), "3");
                                }
                        }
                        int i = ledDisplayMapper.importLedDisplayList(ledDisplayList);
                        if (i > 0) {
                                //1表示导入成功，2表示导入失败
                                return JsonData.success(MessageFormat.format("导入成功！从Excel导入数据一共 {0} 行", ledDisplayList.size()));
                        } else {
                                return JsonData.fail("2");
                        }
                } else {
                        return JsonData.fail("导入数据为空", "3");
                }

        }
}
