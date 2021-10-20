package com.intplog.mcs.weigh;

import com.intplog.mcs.bean.model.McsModel.McsLog;
import com.intplog.mcs.common.SpringContextUtil;
import com.intplog.mcs.enums.McsLogType;
import com.intplog.mcs.service.McsService.McsLogService;
import com.intplog.mcs.weigh.decoder.WeighDecoder;
import com.intplog.mcs.weigh.encoder.WeighEncoder;
import com.intplog.mcs.weigh.handler.WeighHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/4 15:24
 */
@Slf4j
public class WeighNet implements Runnable {

    private McsLogService mcsLogService;

    /**
     * 心跳间隔时间（单位秒）
     */
    private final int activeTime = 3;

    /**
     * plc连接端口
     */
    private int port;

    /**
     * plc设备IP
     */
    private String ip;

    /**
     * PLC设备编号
     */
    private String connectId;

    /**
     *
     */
    private String type;

    /**
     * 事件池
     */
    private EventLoopGroup group;

    private WeighHandler weighHandler = null;

    /**
     * 是否连接
     *
     * @param
     * @return boolean
     * @date 2020/9/4 17:59
     * @author szh
     */
    public boolean isConnected() {
        boolean flag = false;
        if (weighHandler != null) {
            flag = weighHandler.isConnected();
        }
        return flag;
    }


    @Override
    public void run() {
        init();
        long temp;
        boolean connected=false;
        while (true) {
            try {
                if (isConnected()){
                    connected=true;
                }
                else {
                    if (connected){
                    }
                    //客户端连接
                    connect();
                }
                Thread.sleep(1000 * 10);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 初始化
     * @date 2020/9/7 17:52
     * @author szh
     * @param
     * @return void
     */
    private void init(){
        mcsLogService= SpringContextUtil.getBean(McsLogService.class);
    }

    /**
     * 客户端连接
     *
     * @param
     * @return void
     * @date 2020/9/7 13:45
     * @author szh
     */
    private void connect() {
        if (isConnected() || StringUtil.isNullOrEmpty(ip)) {
            return;
        }

        Bootstrap bootstrap = new Bootstrap();
        this.group = new NioEventLoopGroup();

        bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast("WeighDecoder", new WeighDecoder());
                        channel.pipeline().addLast("WeighEncoder", new WeighEncoder());
                        channel.pipeline().addLast(new IdleStateHandler(activeTime, activeTime, activeTime));
                        weighHandler = new WeighHandler(connectId,type);
                        channel.pipeline().addLast("WeighHandler", weighHandler);
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(this.ip, this.port);
        channelFuture.awaitUninterruptibly(1000 * 10);

        if (!channelFuture.isSuccess()) {
            log.info("连接【" + this.ip + "】超时！");
            McsLog mcs1 = new McsLog();
            mcs1.setId(com.intplog.mcs.utils.StringUtil.getUUID32());
            mcs1.setCreateTime(new Date());
            mcs1.setContent("动态称:" + this.ip + "连接超时");
            mcs1.setType("动态称连接");
            mcsLogService.insertMcsLog(mcs1);
            return;
        }
        else {
            log.info("连接【" + this.ip + "】成功！");

            McsLog mcs = new McsLog();
            mcs.setId(com.intplog.mcs.utils.StringUtil.getUUID32());
            mcs.setCreateTime(new Date());
            mcs.setContent("动态称:" + this.ip + "连接成功");
            mcs.setType("动态称连接");
            mcsLogService.insertMcsLog(mcs);
        }

        //注册关闭事件
        channelFuture.channel().closeFuture().addListener(a -> {
            close();
        });

    }

    /**
     * 客户端关闭
     *
     * @param
     * @return void
     * @date 2020/9/7 15:47
     * @author szh
     */
    public void close() {
        //关闭客户端线程组
        if (group != null) {
            group.shutdownGracefully();
        }
        log.info(this.ip + "关闭连接，释放对象");
    }

    //region 构造方法
    public WeighNet() {
    }

    public WeighNet(int port, String ip, String connectId,String type) {
        this.port = port;
        this.ip = ip;
        this.connectId = connectId;
        this.type=type;
    }
    //endregion
}
