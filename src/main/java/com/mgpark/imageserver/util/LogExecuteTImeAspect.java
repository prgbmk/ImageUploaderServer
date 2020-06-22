package com.mgpark.imageserver.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogExecuteTImeAspect {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(LogExecuteTImeAspect.class);

    @Around("@annotation(LogExecuteTime)")
    public Object logExecutionTIme(ProceedingJoinPoint jointPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = jointPoint.proceed();

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());

        return proceed;
    }

}
