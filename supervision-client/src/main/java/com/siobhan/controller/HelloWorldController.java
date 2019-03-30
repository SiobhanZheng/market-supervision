package com.siobhan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by siobhan.zheng on 2019/3/25
 */
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String sayHello(){
        return "hello boys";
    }
    @RequestMapping("/ainsurtech/security/loginCheck")
    public String test(){
        System.out.println("1111");
        return null;
    }
}
