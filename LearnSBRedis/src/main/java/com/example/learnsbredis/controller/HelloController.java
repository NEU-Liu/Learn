package com.example.learnsbredis.controller;

import com.example.learnsbredis.component.IConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujd65
 * @date 2021/11/17 11:22
 **/
@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private IConfiguration configuration;

    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello";
    }


    @RequestMapping(value = "redis")
    public String helloRedis(){
        redis.opsForValue().set("HelloRedisKey","HelloRedisValue");
        return redis.opsForValue().get("HelloRedisKey");
    }

    @GetMapping(value = "/helloConfiguration")
    public String helloConfiguration(){
        return configuration.getId() + "\t" + configuration.getName();
    }
}
