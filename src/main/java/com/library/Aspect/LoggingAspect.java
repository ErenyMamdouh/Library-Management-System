package com.library.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.library.Service.*.*(..))")
    public void serviceLayer() {}

    // Logging method calls
    @Before("serviceLayer()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        logger.info("Method called: " + joinPoint.getSignature().getName() + " with arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    // Logging exceptions
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName() + " with message: " + ex.getMessage());
    }

    // Logging performance metrics
    @Around("serviceLayer()")
    public Object logPerformanceMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Method " + joinPoint.getSignature().getName() + " executed in " + timeTaken + "ms");
        return result;
    }

}
