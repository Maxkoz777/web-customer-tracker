package com.maxkoz777.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.maxkoz777.springdemo.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.maxkoz777.springdemo.dao.*.*(..))")
    private void forDAOPackage() {}

    @Pointcut("execution(* com.maxkoz777.springdemo.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("forDAOPackage() || forControllerPackage() || forServicePackage()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>  in  @Before: calling method: " + method);

        Object[] args = joinPoint.getArgs();

        for(Object o : args) {
            logger.info("=====>>  argument: " + o);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>  in  @AfterReturning: calling method: " + method);

        logger.info("=====>>  result is: " + result);

    }

}
