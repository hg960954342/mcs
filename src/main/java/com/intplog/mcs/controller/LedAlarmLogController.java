package com.intplog.mcs.controller;

import com.intplog.mcs.service.LedAlarmLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liaoliming
 * @Date 2019-10-10 10:59
 */
@Controller
@RequestMapping("/ledAlarm")
public class LedAlarmLogController {

        @Autowired
        private LedAlarmLogService ledAlarmLogService;

        /**
         * 显示list页面
         *
         * @return
         */
        @GetMapping("/")
        public String ledalarms() {

                return "ledalarm/list";
        }

        /**
         * 模糊查询
         *
         * @param createTime
         * @param page
         * @param limit
         * @return
         */
        @RequestMapping("/data")
        @ResponseBody
        public Object toData(String createTime, int page, int limit) {

                String ctime = createTime.equals("") ? null : createTime;
                return ledAlarmLogService.getAll(ctime, page, limit);
        }

}
