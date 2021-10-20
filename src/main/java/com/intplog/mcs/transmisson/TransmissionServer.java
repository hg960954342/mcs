package com.intplog.mcs.transmisson;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/3 11:11
 */
@Component
@Order(Integer.MAX_VALUE)
public class TransmissionServer {

    /**
     * 断线重连时间
     */
    public static final int reconnectTime = 1000 * 5;

    /**
     * 直行方向命令
     */
    public static final int STR = 1;

    /**
     * 左转方向命令
     */
    public static final int LEFT = 2;

    /**
     * 右转方向命令
     */
    public static final int RIGHT = 3;

    public static TransmissionControl transmissionControl;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        connectTransmissionPLC();
    }

    /**
     * 关闭连接
     */
    @PreDestroy
    public void close() {
        transmissionControl.close();
    }

    /**
     * 创建PLC对象
     *
     * @param
     * @return void
     * @date 2020/9/3 15:47
     * @author szh
     */
    private void connectTransmissionPLC() {
        transmissionControl = new TransmissionControl();
        new Thread(transmissionControl).start();
    }

}
