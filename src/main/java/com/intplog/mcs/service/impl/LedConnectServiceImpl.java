package com.intplog.mcs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.LedConnect;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.LedConnectMapper;
import com.intplog.mcs.service.LedConnectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-11 09:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LedConnectServiceImpl implements LedConnectService {
        @Resource
        private LedConnectMapper ledConnectMapper;


        @Override
        public List<LedConnect> getExcelExport(String id, String name) {
                return ledConnectMapper.getList(id, name);
        }

        @Override
        public PageData getAll(String id, String name, int pageNum, int pageSize) {
                PageData pd = new PageData();
                Page<Object> page = PageHelper.startPage(pageNum, pageSize);
                List<LedConnect> all = ledConnectMapper.getList(id, name);
                pd.setMsg("");
                pd.setCount(page.getTotal());
                pd.setCode(0);
                pd.setData(all);
                return pd;

        }

        @Override
        public List<LedConnect> getAll() {
                return ledConnectMapper.getAll();
        }

        /**
         * 根据编号查数据
         *
         * @param id
         * @return
         */
        @Override
        public LedConnect getLedConnectById(String id) {
                return ledConnectMapper.getLedConnectById(id);
        }

        /**
         * 添加信息
         *
         * @param ledConnect
         * @return
         */
        @Override
        public int insertLedConnect(LedConnect ledConnect) {
                return ledConnectMapper.insertLedConnect(ledConnect);
        }

        /**
         * 批量添加信息
         * @param ledConnectList
         * @return
         */
        @Override
        public int batchInsert(List<LedConnect> ledConnectList) {

                return ledConnectMapper.batchInsert(ledConnectList);
        }

        /**
         * 更新数据
         *
         * @param ledConnect
         * @return
         */
        @Override
        public int updateLedConnect(LedConnect ledConnect) {
                return ledConnectMapper.updateLedConnect(ledConnect);
        }

        /**
         * 删除数据
         *
         * @param id
         * @return
         */
        @Override
        public PageData deleteLedConnectById(String id) {
                PageData pd = new PageData();
                int i = ledConnectMapper.deleteLedConnect(id);

                pd.setCode(0);
                pd.setMsg("");

                if (i < 1) {
                        pd.setMsg("删除数据失败！");
                }
                return pd;
        }

}
