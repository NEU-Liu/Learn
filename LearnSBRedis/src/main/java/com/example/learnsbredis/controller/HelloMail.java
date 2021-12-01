package com.example.learnsbredis.controller;

import com.example.learnsbredis.service.EmailService;
import com.example.learnsbredis.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * @author liujd65
 * @date 2021/11/18 9:45
 **/
@RestController
public class HelloMail {


    EmailService email;

    @Autowired
    public HelloMail(EmailServiceImpl email) {
        this.email = email;
    }

    @ResponseBody
    @RequestMapping("/mail")
    public void mailSend(){
        email.sendHtmlMail("1531653614@qq.com","你好","World");
    }






}
