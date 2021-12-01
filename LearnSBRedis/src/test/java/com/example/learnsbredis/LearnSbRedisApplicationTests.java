package com.example.learnsbredis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

import javax.activation.FileTypeMap;

@SpringBootTest
class LearnSbRedisApplicationTests {

    @Test
    void contextLoads() {
        ConfigurableMimeFileTypeMap fileTypeMap = new ConfigurableMimeFileTypeMap();
        fileTypeMap.afterPropertiesSet();
        System.out.println(fileTypeMap);
        FileTypeMap fileTypeMap1 = fileTypeMap;

    }

}



