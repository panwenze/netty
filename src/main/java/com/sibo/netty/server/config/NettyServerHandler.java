package com.sibo.netty.server.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @description: 处理器
 * @author: panwz
 * @create: 2018-11-09 15:26
 **/
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {


    private final static Map<String, Channel> userMap;

    static {
        userMap = Collections.synchronizedMap(new HashMap<String, Channel>());
    }


    /**
     * 数据接收
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("服务器接收到" + ctx.channel().remoteAddress() + "的消息:" + msg);
    }


    /**
     * channel建立连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("终端[ip:" + ctx.channel().remoteAddress() + "]连接");
        ctx.writeAndFlush("CONNECT SUCCESS");
        userMap.put(ctx.channel().remoteAddress().toString(), ctx.channel());
        super.channelActive(ctx);
    }

    /**
     * 关闭时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("终端[ip:" + ctx.channel().remoteAddress() + "]关闭连接");
        super.channelInactive(ctx);
        userMap.remove(ctx.channel().remoteAddress().toString());
    }

    /**
     * 心跳检测
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {//超时事件
            IdleStateEvent idleEvent = (IdleStateEvent) evt;
            if (idleEvent.state() == IdleState.READER_IDLE) {//读
                log.info("终端[" + ctx.channel().remoteAddress() + "]-处于空闲状态，通道关闭");
                userMap.remove(ctx.channel().remoteAddress().toString());
                ctx.channel().close();//超过时间没有收到客户端的消息，则关闭
            } else if (idleEvent.state() == IdleState.WRITER_IDLE) {//写

            } else if (idleEvent.state() == IdleState.ALL_IDLE) {//全部

            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Netty服务出现异常:" + cause.getMessage());
        // 当出现异常就关闭连接
        ctx.channel().close();

    }

}
