package com.intplog.mcs.weigh.handler;

import com.intplog.mcs.weigh.WeighInit;
import com.intplog.mcs.weigh.bean.WeighData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/4 17:51
 */
@Component
@Slf4j
public class WeighHandler extends SimpleChannelInboundHandler<WeighData> {

    /**
     * 连接状态
     */
    private boolean connected;

    /**
     * 设备编号
     */
    private String connectId;

    /**
     * 类型（1动态秤，2静态秤）
     */
    private String type;

    public boolean isConnected() {
        return this.connected;
    }


    //region 构造方法
    public WeighHandler() {
    }

    public WeighHandler(String equipId, String type) {
        this.connectId = equipId;
        this.type = type;
        init();
    }
    //endregion


    /**
     * 初始化
     *
     * @param
     * @return void
     * @date 2020/9/7 16:41
     * @author szh
     */
    public void init() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WeighData msg) throws Exception {
        try {
            msg.setNumber(this.connectId);
            msg.setType(this.type);
//            System.out.println(msg.toString());
            WeighInit.queue.add(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.connected = true;
        ctx.writeAndFlush(1);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.connected = false;

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            switch (event.state()) {
                case READER_IDLE:
                case WRITER_IDLE:
                    ctx.writeAndFlush(1);
                    break;
                case ALL_IDLE:
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.connected = false;
        cause.printStackTrace();
        log.error(cause.toString());
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.connected = false;
        log.info("设备连接中断:" + this.connectId);
    }
}
