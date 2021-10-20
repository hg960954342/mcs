package com.intplog.mcs.weigh.decoder;

import com.intplog.mcs.weigh.bean.WeighData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/7 15:52
 */
@Slf4j
public class WeighDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        try {
        /*
        [2B 20 20 20 20 20 30 2E 30 0D 0A ]
         */
        //+     0.0
            //+     0.0
            //+     0.0
            //+     0.0
            //+     0.0
            //+     0.0
            //[30 30 30 30 32 33 ]
            String address = getAddress(ctx);
            String data = in.toString(CharsetUtil.US_ASCII);
//            log.info(address + "收到数据:" + data);
            double weigh = 0;
            String value = analyData(data);
            if (value==null){
                return;
            }
            weigh=Double.parseDouble(value);
            WeighData weighData = new WeighData();
            weighData.setNumber(address);
            weighData.setWeight(weigh);

            out.add(weighData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.clear();
        }
    }

    /**
     * 获取服务端地址 ip:port
     *
     * @param ctx
     * @return java.lang.String
     * @date 2020/9/8 10:28
     * @author szh
     */
    private String getAddress(ChannelHandlerContext ctx) {
        String address = "";
        if (ctx != null) {
            Channel channel = ctx.channel();
            String socketAddress = channel.remoteAddress().toString();
            address = socketAddress.substring(1);
        }
        return address;
    }

    private String analyData(String data){
        String value=null;
        if (data.length()==6){
            value=data;
        }
        else if (data.startsWith("+")||data.startsWith("-")){
            value = data.substring(0, 1) + data.substring(1, 9).replace(" ", "");
        }
        return value;
    }
}
