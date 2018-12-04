package com.yichuizi.loglibrary.core;


import com.yichuizi.loglibrary.annotation.LogAfterReturnFilter;
import com.yichuizi.loglibrary.annotation.LogAroundFilter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者： duanyikang on 2018/12/2.
 * 描述：
 */
@Aspect
public class LogFilterAspect {

    /**
     * 环绕通知, 围绕着方法执行
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution(@com.yichuizi.loglibrary.annotation.LogAroundFilter * *(..))")
    public void aroundLogPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new Exception("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        final LogAroundFilter logFilter = methodSignature.getMethod().getAnnotation(LogAroundFilter.class);
        if (logFilter == null) {
            return;
        }
        joinPoint.proceed();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("打个点：" + logFilter.Log());
            }
        }).start();
    }

    /**
     * 回通知, 在方法返回结果之后执行
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution(@com.yichuizi.loglibrary.annotation.LogAfterReturnFilter * *(..))")
    public Object afterRetrunLogPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new Exception("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        final LogAfterReturnFilter logFilter = methodSignature.getMethod().getAnnotation(LogAfterReturnFilter.class);
        if (logFilter == null) {
            return null;
        }
        Object result = joinPoint.proceed();
        if ((boolean) result) {
            System.out.println("方法执行成功：" + logFilter.Log());
        } else {
            System.out.println("方法执行失败：" + logFilter.Log());
        }
        return result;
    }

    @Before("execution(@com.yichuizi.loglibrary.annotation.LogBeforeFilter * *(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("执行之前打个点：" +joinPoint.getSignature().getName());
    }

    @After("execution(@com.yichuizi.loglibrary.annotation.LogAfterFilter * *(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("执行之后打个点：" +joinPoint.getSignature().getName());
    }

}
