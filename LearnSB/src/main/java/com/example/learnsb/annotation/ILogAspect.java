package com.example.learnsb.annotation;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author liujd65
 * @date 2021/12/7 14:13
 **/

@Aspect
@Component
@Log4j2
public class ILogAspect {
    @Pointcut(value = "@annotation(iLog)")
    public void pointcut(ILog iLog) {
    }


    @Before("@annotation(iLog)")
    public void before(JoinPoint joinPoint, ILog iLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("MethodSignature:{}, Method:{}", signature, method);
    }


    @Around(value = "@annotation(iLog)")
    public Object operationLogRecord(ProceedingJoinPoint joinPoint, ILog iLog) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //如果原方法正常执行完毕，那么需要记录操作日志
        saveOperationLog(joinPoint, iLog, request);
        return joinPoint.proceed();
    }

    private void saveOperationLog(ProceedingJoinPoint joinPoint, ILog operationLog, HttpServletRequest request) {
        Object[] allParams = joinPoint.getArgs();
        for (Object param : allParams) {
            System.out.println(param);
        }

        log.info("ILog description: {}", operationLog.description());
    }


}
