package com.example.learnsb.controller;


import com.example.learnsb.annotation.ILog;
import org.springframework.web.bind.annotation.*;

@RestController

public class Hello {

    @RequestMapping(value = "/hi")
    @ResponseBody
    @ILog(description = "IAnnotation!")
    public String hello(){
        return "Hello";
    }


}
