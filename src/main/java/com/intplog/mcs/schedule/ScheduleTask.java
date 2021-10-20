package com.intplog.mcs.schedule;

import com.intplog.mcs.service.InterfaceLogService;
import com.intplog.mcs.transmisson.TransmissionControl;
import com.intplog.mcs.transmisson.TransmissionServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liaoliming
 * @Date 2019-10-31 10:45
 * 定时任务与任务计划
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTask {

    @Autowired
    private InterfaceLogService interfaceLogService;


    @Scheduled(cron = "0/1 * * * * ?")
    private void sendStaticWeightToWMS() {
        TransmissionControl plc1 = TransmissionServer.transmissionControl;
        plc1.staticWeight();
    }


    //endregion


}

