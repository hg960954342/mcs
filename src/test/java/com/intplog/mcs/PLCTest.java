package com.intplog.mcs;

import com.intplog.mcs.common.Utils;
import com.intplog.siemens.SiemensNet;
import com.intplog.siemens.bean.ResultData;
import com.intplog.siemens.enumerate.SiemensPLCS;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.intplog.siemens.enumerate.SiemensPLCS.S1200;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/7/5 19:55
 */
public class PLCTest {
    public static void main(String[] args) {
        TransmissionDriver transmissionDriver = new TransmissionDriver();
        transmissionDriver.createPlc("192.166.11.22", S1200);
        transmissionDriver.start();
    }


}

class TransmissionDriver extends Thread {

    //PLC对象
    private SiemensNet siemensNet;
    private SiemensNet writeSiemensNet;

    private boolean connected;

    //IP地址
    public String ip;

    /**
     * 线程池
     */
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 是否连接
     *
     * @return
     */
    public boolean isConnected() {
        return siemensNet.isConnectd() && writeSiemensNet.isConnectd();
    }

    /**
     * 创建PLC对象
     *
     * @param ip
     */
    public void createPlc(String ip, SiemensPLCS type) {
        siemensNet = new SiemensNet(type);
        writeSiemensNet = new SiemensNet(type);
        this.ip = ip;
    }


    /**
     * 连接PLC
     */
    private void connected() {
        if (siemensNet.isConnectd() && writeSiemensNet.isConnectd()) {
            return;
        }
        try {
            if (!siemensNet.isConnectd()) {
                siemensNet.connect(ip);
            }
            if (!writeSiemensNet.isConnectd()) {
                writeSiemensNet.connect(ip);
            }
            connected = siemensNet.isConnectd() && writeSiemensNet.isConnectd();
        } catch (Exception ex) {
            connected = false;
        }
        if (connected) {
            String content = "PLC:" + this.ip + "   连接成功";
            System.out.println(content);
        } else {
            String content = "PLC:" + this.ip + "   连接失败";
            System.out.println(content);
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (connected) {
            connected = false;
            siemensNet.close();
            writeSiemensNet.close();
        }
    }


    @Override
    public void run() {
        long temp = 0;
        Date begin;
        boolean isCon = false;
        createPool();
        while (true) {
            try {
                begin = new Date();
                if (isConnected()) {

                    isCon = true;
                    connected = true;

                } else {
                    connected();
                }
                if (isCon) {
                    temp = Utils.getTimeDifference(begin);
                    Thread.sleep(temp);
                } else {
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createPool() {

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                int count = 0;
                int ero = 0;
                String group = "T01";
                while (true) {
                    try {
                        if (isConnected()) {
                            count++;
//                          ResultData resultData = siemensNet.readBytes("DB5100.DBB2900", 60);
                            ResultData resultData = siemensNet.readBytes("DB100.DBB0", 82);
                            if (!resultData.isSuccess()) {
                                ero++;
                                System.out.println(count + "失败" + ero + "  R01读取失败:" + resultData);
                            }
//                          System.out.println("R01读取:"+resultData+",this:"+siemensNet.hashCode());
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                int count = 0;
                int ero = 0;
                String group = "T02";
                while (true) {
                    try {
                        if (isConnected()) {
                            count++;
//                            ResultData resultData = siemensNet.readBytes("DB5100.DBB2780", 60);
                            ResultData resultData = siemensNet.readBytes("DB304.DBB0", 130);

                            if (!resultData.isSuccess()) {
                                ero++;
                                System.out.println(count + "失败" + ero + "  R02读取失败:" + resultData);
                                continue;
                            }
//                            System.out.println("R02读取:"+resultData+",this:"+siemensNet.hashCode());
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                int count = 0;
                int ero = 0;
                String group = "T03";
                while (true) {
                    try {
                        if (isConnected()) {
                            count++;
                            ResultData resultData = siemensNet.readBytes("DB5100.DBB400", 100);
//                            ResultData resultData = siemensNet.readBytes("DB305.DBB0", 130);

                            if (!resultData.isSuccess()) {
                                ero++;
                                System.out.println(count + "失败" + ero + "  2号拣选站下层输送线读取失败:" + resultData);
                            }
//                            System.out.println("R03读取:"+resultData+",this:"+siemensNet.hashCode());
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };





//        cachedThreadPool.execute(thread1);
//        cachedThreadPool.execute(thread2);
//        cachedThreadPool.execute(thread3);
//        cachedThreadPool.execute(thread4);
//        cachedThreadPool.execute(thread5);
//        cachedThreadPool.execute(thread6);

    }

}
