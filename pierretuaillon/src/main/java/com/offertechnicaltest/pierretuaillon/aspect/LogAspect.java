package com.offertechnicaltest.pierretuaillon.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for log inputs and outputs of each call as well as the processing time
 * @author pierretuaillon
 *
 */

@Aspect
@Component
public class LogAspect {
	
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
	
	/**
	 * Method call when @Times is used
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(Timed)")
	public Object logTime(ProceedingJoinPoint  joinPoint) throws Throwable {
		log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        
        log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), proceed);
        return proceed;
	}
}