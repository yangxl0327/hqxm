package com.yang.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;

@Aspect
public class CacheAspect {
    private StringRedisTemplate stringRedisTemplate;
    @Around("@annotation(com.yang.Annotation.AddCache)")
    public  Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        //Key
        String key1 = proceedingJoinPoint.getTarget().getClass().getName();
        //key
        String key = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            key += arg;
            key += ",";
        }
        Object o = stringRedisTemplate.opsForHash().get(key1, key);
        if(o!=null){
            return o;
        }else {
            try {
                Object  proceed = proceedingJoinPoint.proceed();
                return  proceed;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return  null;
            }
        }

    }
    @Around("@annotation(com.yang.Annotation.DelCache)")
    public  Object delCache(ProceedingJoinPoint proceedingJoinPoint){
        String name = proceedingJoinPoint.getTarget().getClass().getName();
        stringRedisTemplate.delete(name);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            return  proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
