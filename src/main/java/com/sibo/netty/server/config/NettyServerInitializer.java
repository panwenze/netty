package com.sibo.netty.server.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @description: 服务端初始化
 * @author: panwz
 * @create: 2018-11-09 15:25
 **/
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {


    /**
     * 初始化
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 设置心跳检测
        pipeline.addLast("stateHandler", new IdleStateHandler(6000, 0, 0));
        // 编码器
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.US_ASCII));
        // 解码器
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.US_ASCII));
        // 消息处理
        pipeline.addLast("handler", new NettyServerHandler());
    }
}
