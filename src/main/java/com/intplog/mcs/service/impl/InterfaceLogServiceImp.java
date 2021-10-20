package com.intplog.mcs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.intplog.mcs.bean.model.InterfaceLog;
import com.intplog.mcs.bean.viewmodel.PageData;
import com.intplog.mcs.common.JsonData;
import com.intplog.mcs.mapper.InterfaceLogMapper;
import com.intplog.mcs.service.InterfaceLogService;
import com.intplog.mcs.utils.DateHelpUtil;
import com.intplog.mcs.utils.JsonConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 日志接口类
 *
 * @author liaoliming
 * @Date 2019-06-14 09:28
 * @Version 1.0
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceLogServiceImp implements InterfaceLogService {

        @Resource
        private InterfaceLogMapper interfaceLogMapper;

        @Override
        public List<InterfaceLog> getInterfaceLogById(String id) {
                return interfaceLogMapper.getInterfaceLogById(id);
        }

        /**
         * @param pageNum
         * @param pageSize
         * @return
         *
         */
        @Override
        public PageData getAll(String method, String rqTime, int pageNum, int pageSize) {
                PageData pd = new PageData();
                Page<Object> page = PageHelper.startPage(pageNum, pageSize);
                List<InterfaceLog> all = interfaceLogMapper.getList(method, rqTime);
                pd.setMsg("");
                pd.setCount(page.getTotal());
                pd.setCode(0);
                pd.setData(all);
                return pd;
        }


    @Override
        public int insertInterfaceLog(String method, Object rq, Date rqTime, JsonData rp, Date rpTime) {
                InterfaceLog log = new InterfaceLog();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                Integer status = 0; //0为正常，1为错误

                String rpStr = rp == null ? "" : JsonConvertUtils.objToStr(rp);

                if (rp == null || !rp.isSuccess()) {
                        status = 1;
                }

                log.setMethod(method);
                log.setId(id);
                log.setRpTime(rpTime);
                log.setRpData(rpStr);
                log.setRqTime(rqTime);
                log.setRqData(JsonConvertUtils.objToStr(rq));
                log.setStatus(status);
                return interfaceLogMapper.insertInterfaceLog(log);
        }

        @Override
        public int insertInterfaceLog(String method, Object rq, Date rqTime, Object rp, Date rpTime) {
                InterfaceLog log = new InterfaceLog();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                Integer status = 0; //0为正常，1为错误

                String rpStr = rp == null ? "" : JsonConvertUtils.objToStr(rp);
                log.setMethod(method);
                log.setId(id);
                log.setRpTime(rpTime);
                log.setRpData(rpStr);
                log.setRqTime(rqTime);
                log.setRqData(JsonConvertUtils.objToStr(rq));
                log.setStatus(status);
                return interfaceLogMapper.insertInterfaceLog(log);
        }

        @Override
        public int insertInterFaceLogByResPCross(String method, Object rq, Date rqTime, JsonData rp, Date rpTime) {
                InterfaceLog log = new InterfaceLog();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                Integer status = 0; //0为正常，1为错误

                String rpStr = rp == null ? "" : JsonConvertUtils.objToStr(rp);

                if (rp == null || !rp.isSuccess()) {
                        status = 1;
                }

                log.setMethod(method);
                log.setId(id);
                log.setRpTime(rpTime);
                log.setRpData(rpStr);
                log.setRqTime(rqTime);
                log.setRqData(JsonConvertUtils.objToStr(rq));
                log.setStatus(status);
                return interfaceLogMapper.insertInterfaceLog(log);
        }

        /**
         * 返回比当前系统日期早day天的日期
         */
        @Override
        public int deleteLog(int day) {
                Date dt = DateHelpUtil.getDate(day);
                return interfaceLogMapper.deleteLog(dt);
        }

}
