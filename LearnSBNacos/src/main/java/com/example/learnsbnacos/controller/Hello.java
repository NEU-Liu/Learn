package com.example.learnsbnacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujd65
 * @date 2021/11/30 16:27
 **/
@RestController
public class Hello {

    @RequestMapping(value = "hi")
    public String hello(){
        return "Hello";
    }

}
