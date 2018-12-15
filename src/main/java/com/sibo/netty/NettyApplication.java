package com.sibo.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class NettyApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
        log.info("web服务启动成功！");
    }

    /**
     * tomcat启动
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NettyApplication.class);
    }


}
