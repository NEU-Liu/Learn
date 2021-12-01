package com.example.learnsbannotation.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujd65
 * @date 2021/11/23 9:48
 **/
@Configuration
public class BeanConfiguration {

    @Conditional({WindowsCondition.class})
    @Bean(name = "windows")
    public Target target1(){
        return new Target("1","One");
    }

    @Conditional({LinuxCondition.class})
    @Bean(name = "linux")
    public Target target2(){
        return new Target("2","Two");
    }

}
