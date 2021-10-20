package com.intplog.mcs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.SysParam;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.SysParamMapper;
import com.intplog.mcs.service.SysParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JiangZhongXing
 * @create 2019-01-11 10:34 AM
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysParamServiceImpl implements SysParamService {

        @Resource
        private SysParamMapper sysParamMapper;

        /**
         * @param pageNum
         * @param pageSize
         * @return
         */
        @Override
        public PageData getAll(String code, String name, int pageNum, int pageSize) {
                PageData pd = new PageData();
                Page<Object> page = PageHelper.startPage(pageNum, pageSize);
                List<SysParam> all = sysParamMapper.getList(code, name);
                pd.setMsg("");
                pd.setCount(page.getTotal());
                pd.setCode(0);
                pd.setData(all);
                return pd;
        }

        @Override
        public SysParam getSysParamByCode(String code) {
                return sysParamMapper.getSysParamByCode(code);
        }

        @Override
        public int insertSysParam(SysParam sysParam) {
                return sysParamMapper.insertSysParam(sysParam);
        }

        @Override
        public int getTotal() {
                return sysParamMapper.getTotal();
        }

        @Override
        public List<SysParam> getKeyAndValue() {
                return sysParamMapper.getAll();
        }


        @Override
        public int updateSysParam(SysParam sysParam) {
                return sysParamMapper.updateSysParam(sysParam);
        }

        @Override
        public int updateSysParamValue(String code, String value) {
                return sysParamMapper.updateSysParamValue(code, value);
        }

        @Override
        public PageData deleteSysParamBymId(String mId) {
                PageData pd = new PageData();
                int i = sysParamMapper.deleteSysParam(mId);

                pd.setCode(0);
                pd.setMsg("");

                if (i < 1) {
                        pd.setMsg("删除数据失败！");
                }
                return pd;
        }
}
