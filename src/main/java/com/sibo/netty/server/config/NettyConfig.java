package com.sibo.netty.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: panwz
 * @create: 2018-11-20 19:54
 **/
@Component
public class NettyConfig {
    private static int nettyPort;
    private static String nettyHost;

    @Value("${netty.port}")
    public void setNettyPort(int nettyPort) {
        this.nettyPort = nettyPort;
    }

    @Value("${netty.host}")
    public void setHost(String nettyHost) {
        this.nettyHost = nettyHost;
    }


    public int getNettyPort() {
        return this.nettyPort;
    }

    public String getNettyHost() {
        return this.nettyHost;
    }
}
