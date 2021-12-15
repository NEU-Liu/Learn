package com.example.learnsb.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author liujd65
 * @date 2021/12/7 15:34
 **/
@Log4j2
@Aspect
@Component
public class IAspect {


    @Pointcut(value = "execution(* com.example.learnsb.controller.Hello.*(..))")
    public void IPointcut() {

    }

    @Before(value = "IPointcut()")
    public void beforeAdvice() {
        log.info("Before Advice!");
    }

    @After(value = "IPointcut()")
    public void afterAdvice() {
        log.info("After Advice!");
    }

    @AfterReturning(value = "IPointcut()")
    public void afterReturning() {
        log.info("After Returning!");
    }

    @Around(value = "IPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Object[] array = pjp.getArgs();
        log.info("调用前:" + className + "," + className + "args:" + objectMapper.writeValueAsString(array));
        Object obj = pjp.proceed();
        log.info("调用后:" + className + "," + methodName + "返回:" + objectMapper.writeValueAsString(obj));
        return obj;
    }

}
