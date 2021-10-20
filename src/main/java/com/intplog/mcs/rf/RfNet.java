package com.intplog.mcs.rf;

import com.intplog.mcs.rf.handler.RfHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Classname RF
 * @Description TODO
 * @Date 2020/2/25 15:17
 * @Created by jiangzhongxing
 */
@Component
public class RfNet {

    @Resource
    private RfHandler rfHandler;

    /**
     * 连接超时时间
     */
    public static int timeout = 30;

    /**
     * 监听端口
     */
    private final int serverPort = 8087;

    /**
     *
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     *
     */
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    /**
     * 报文队列
     */
    public static Queue<String> dataQue = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void init() {
        startListen();
    }


    /**
     * 启动监听
     */
    public void startListen() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("RfHandler", rfHandler);
                        }
                    });

            ChannelFuture future = b.bind(serverPort);//开启需要监听 的端口
            future.channel().closeFuture();
            System.out.println("启动 " + serverPort + " 成功");
        } catch (Exception e) {
            System.out.println("启动监听服务异常：" + e.getMessage());
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workGroup.shutdownGracefully().syncUninterruptibly();
        System.out.println("关闭 Netty 成功");
    }
}
