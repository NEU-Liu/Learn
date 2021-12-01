package com.example.learnsbredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class LearnSbRedisApplication {

    public static void main(String[] args) {

//        SpringApplication application = new SpringApplication(LearnSbRedisApplication.class);
//        application.setApplicationStartup(new BufferingApplicationStartup(2048));
//        application.run(args);

         SpringApplication.run(LearnSbRedisApplication.class, args);
    }

}
