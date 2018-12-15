package com.sibo.netty;

import com.sibo.netty.server.NettyServer;
import com.sibo.netty.server.config.NettyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: panwz
 * @create: 2018-12-15 10:52
 **/
@Slf4j
@Component
public class NettyServerBoot implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private NettyConfig nettyConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                NettyServer.getInstance().start(nettyConfig.getNettyPort());
            } catch (Exception e) {
                log.error("Netty服务启动异常:" + e.getMessage());
                NettyServer.getInstance().close();
            }
        }
    }
}
