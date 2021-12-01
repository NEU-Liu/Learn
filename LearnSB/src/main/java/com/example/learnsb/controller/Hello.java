package com.example.learnsb.controller;


import org.springframework.web.bind.annotation.*;

@RestController

public class Hello {

    @RequestMapping(value = "/hi")
    @ResponseBody
    public String hello(){
        return "Hello";
    }
}
