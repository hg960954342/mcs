package com.intplog.mcs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.LedAlarmLog;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.mapper.LedAlarmLogMapper;
import com.intplog.mcs.service.LedAlarmLogService;
import com.intplog.mcs.utils.DateHelpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author liaoliming
 * @Date 2019-10-10 10:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LedAlarmLogServiceImpl implements LedAlarmLogService {

        @Resource
        private LedAlarmLogMapper ledAlarmLogMapper;

        @Override
        public PageData getAll(String createTime, int pageNum, int pageSize) {

                PageData pd = new PageData();
                Page<Object> page = PageHelper.startPage(pageNum, pageSize);
                List<LedAlarmLog> all = ledAlarmLogMapper.getList(createTime);
                pd.setMsg("");
                pd.setCount(page.getTotal());
                pd.setCode(0);
                pd.setData(all);
                return pd;
        }

        /**
         * //返回比当前系统日期早day天的日期
         *
         * @param day
         * @return
         */
        @Override
        public int deleteLog(int day) {
                Date dt = DateHelpUtil.getDate(day);
                return ledAlarmLogMapper.deleteLog(dt);
        }

        @Override
        public int insertLedAlarmLog(LedAlarmLog ledAlarmLog) {
                return ledAlarmLogMapper.insert(ledAlarmLog);
        }

        @Override
        public int updateLedAlarmLog(LedAlarmLog ledAlarmLog) {
                return ledAlarmLogMapper.updateByPrimaryKey(ledAlarmLog);
        }
}
