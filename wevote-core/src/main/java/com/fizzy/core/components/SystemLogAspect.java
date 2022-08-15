package com.fizzy.core.components;

import com.fizzy.core.annotation.SystemLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/8/12 10:31
 * description 日志切面
 */
@Aspect
@Component
public class SystemLogAspect {

    @Pointcut(value = "@annotation(com.fizzy.core.annotation.SystemLog)")
    private void logPointcut() {
    }

    @Pointcut(value = "bean(PostController)")
    private void logPointcut2() {
    }

    @After("logPointcut2()")
    private void afterLog() {
        System.out.println("afterLog");
    }

    @Before("logPointcut()")
    private void beforeLog(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        SystemLog systemLog = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(SystemLog.class);
        String actionName = systemLog.actionDescription();
        String actionType =systemLog.actionType();
        String functionName = systemLog.functionName();
        // 参数值
        Map<String, Object> params = getFields(joinPoint);
        System.out.println(actionName);
        System.out.println(actionType);
        System.out.println(functionName);
        System.out.println(params);
    }

    private static Map<String, Object> getFields(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        String[] parameterNames = pnd.getParameterNames(Class.forName(classType).getMethod(methodName, parameterTypes));
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> paramMap = new HashMap();
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
}