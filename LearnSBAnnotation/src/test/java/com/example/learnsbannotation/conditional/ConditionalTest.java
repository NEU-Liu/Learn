package com.example.learnsbannotation.conditional;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author liujd65
 * @date 2021/11/23 9:51
 **/
public class ConditionalTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);

    @Test
    public void test1(){
        Map<String, Target> map = applicationContext.getBeansOfType(Target.class);
        System.out.println(map);
    }

}
