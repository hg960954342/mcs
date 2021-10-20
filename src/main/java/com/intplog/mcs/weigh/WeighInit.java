package com.intplog.mcs.weigh;

import com.intplog.mcs.bean.model.McsModel.McsWeightProperTies;
import com.intplog.mcs.service.McsService.McsWeightProperTiesService;
import com.intplog.mcs.weigh.bean.WeighData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/4 15:29
 */
@Component
public class WeighInit {

    @Autowired
    private McsWeightProperTiesService mcsWeightProperTiesService;

    /**
     * 秤对象<设备编号,对象>
     */
    private Map<String, WeighNet> weightMap = new HashMap();

    /**
     * 秤数据队列
     */
    public static Queue<WeighData> queue = new ConcurrentLinkedQueue<>();

    @PostConstruct
    private void init() {
        //查询所有秤
        List<McsWeightProperTies> weightList = mcsWeightProperTiesService.getAllList();
        for (McsWeightProperTies point : weightList) {
            //秤处理
            WeighNet weighNet = new WeighNet(point.getPort(), point.getIp(), point.getConnectId(), point.getType());
            new Thread(weighNet).start();
            weightMap.put(point.getConnectId(), weighNet);
        }
    }

    @PreDestroy
    private void destroy() {
        for (WeighNet weighNet : this.weightMap.values()) {
            weighNet.close();
        }
    }
}
