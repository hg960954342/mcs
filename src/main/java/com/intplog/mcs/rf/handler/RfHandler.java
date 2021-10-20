package com.intplog.mcs.rf.handler;

import com.intplog.mcs.bean.model.McsModel.McsLog;
import com.intplog.mcs.common.PLCStringToASCLL;
import com.intplog.mcs.common.SpringContextUtil;
import com.intplog.mcs.enums.McsLogType;
import com.intplog.mcs.rf.RfNet;
import com.intplog.mcs.service.McsService.McsLogService;
import com.intplog.mcs.utils.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ChannelHandler.Sharable
@Slf4j
public class RfHandler extends ChannelInboundHandlerAdapter {

    private McsLogService mcsLogService;

    /**
     * RF连接池<ip,通道对象>
     */
    public static Map<String, Channel> rfMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        mcsLogService = SpringContextUtil.getBean(McsLogService.class);
    }


    /**
     * 接受客户端发来的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String data = buf.toString(CharsetUtil.US_ASCII);
        ReferenceCountUtil.release(msg);
        process(data, getAddress(ctx));
    }


    /**
     * 处理获取到的数据
     *
     * @param msg
     * @param address
     * @return void
     * @date 2020/9/3 10:29
     * @author szh
     */
    private void process(String msg, String address) {
        String[] split = msg.split("\r\n");
        log.info("收到数据:" + Arrays.toString(split) + " IP" + address);
        for (String data:split){
            RfNet.dataQue.offer(data);
        }
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("上线：" + getAddress(ctx));
        McsLog mcs = new McsLog();
        mcs.setId(StringUtil.getUUID32());
        mcs.setCreateTime(new Date());
        mcs.setContent("RF:" + getAddress(ctx) + "上线");
        mcs.setType(McsLogType.RFCONNEVT.getDesc());
        String[] arr = getAddress(ctx).split(":");
        mcsLogService.insertMcsLog(mcs);

//        ByteBuf r = Unpooled.copiedBuffer(PLCStringToASCLL.ConvertToASCII("R"), CharsetUtil.UTF_8);
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(PLCStringToASCLL.ConvertToASCII("R"), CharsetUtil.UTF_8));

        if (rfMap.containsKey(arr[0])) {
            rfMap.remove(arr[0]);
        }
        rfMap.put(arr[0], ctx.channel());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("离线：" + getAddress(ctx));
        McsLog mcs = new McsLog();
        mcs.setId(StringUtil.getUUID32());
        mcs.setCreateTime(new Date());
        mcs.setContent("RF:" + getAddress(ctx) + "离线");
        mcs.setType(McsLogType.RFCONNEVT.getDesc());

        String[] arr = getAddress(ctx).split(":");
        mcsLogService.insertMcsLog(mcs);
        if (rfMap.containsKey(arr[0]))
            rfMap.remove(arr[0]);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 获取客户端地址
     *
     * @param ctx
     * @return
     */
    private String getAddress(ChannelHandlerContext ctx) {
        String address = "";
        if (ctx != null) {
            Channel channel = ctx.channel();
            address = channel.remoteAddress().toString().substring(1);
        }
        return address;
    }


    /**
     * 发送数据
     *
     * @param address
     * @return
     */
    public static void sendMessage(String address) {
        if (rfMap.containsKey(address)) {
            Channel context = rfMap.get(address);
            String ms = PLCStringToASCLL.ConvertToASCII("R");
            context.writeAndFlush(Unpooled.copiedBuffer("R", CharsetUtil.US_ASCII));
        }

    }
}