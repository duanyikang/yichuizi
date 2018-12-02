package com.yichuizi.loglibrary.core;

import android.content.Context;

import com.yichuizi.loglibrary.annotation.LogFilter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者： duanyikang on 2018/12/2.
 * 描述：
 */
@Aspect
public class LogFilterAspect {

    @Pointcut("execution(@com.yichuizi.loglibrary.annotation.LogFilter * *(..))")
    public void logFilter() {
    }

    @Around("logFilter()")
    public void aroundLogPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new Exception("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        final LogFilter logFilter = methodSignature.getMethod().getAnnotation(LogFilter.class);
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

//        if ((boolean) joinPoint.proceed()) {
//            System.out.println("方法执行成功：" + logFilter.Log());
//        } else {
//            System.out.println("方法执行失败：" + logFilter.Log());
//        }

    }
}
