package com.sibo.netty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: panwz
 * @create: 2018-11-10 11:30
 **/
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "helloNetty";
    }

}
