package com.sibo.netty.server;

import com.sibo.netty.server.config.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: netty服务器
 * @author: panwz
 * @create: 2018-11-09 15:23
 **/
@Slf4j
@Component
public class NettyServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    private static class SingNettyServer {
        static final NettyServer instance = new NettyServer();
    }

    public static NettyServer getInstance() {
        return SingNettyServer.instance;
    }


    public NettyServer() {
        this.bossGroup = new NioEventLoopGroup();//接收请求的线程池
        this.workerGroup = new NioEventLoopGroup();//处理请求的线程池
        this.serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new NettyServerInitializer());//为处理accept客户端的channel中的pipeline添加自定义处理函数
    }


    /**
     * 启动服务器方法
     */
    public void start(int port) {

        // 绑定端口（实际上是创建serversocketchannnel，并注册到eventloop上），同步等待完成，返回相应channel
        this.channelFuture = serverBootstrap.bind(port);
        log.info("netty服务启动成功: [port:" + port + "]");
    }

    public void close() {
        //退出
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
