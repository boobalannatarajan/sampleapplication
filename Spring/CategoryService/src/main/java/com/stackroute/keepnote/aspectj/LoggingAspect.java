package com.stackroute.keepnote.aspectj;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("==============@Before==============");
		logger.debug("method name : " + joinPoint.getSignature().getName());
		logger.debug("***************At last @Before*******************");
	}

	@After("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("==============@After==============");
		logger.debug("method name : " + joinPoint.getSignature().getName());
		logger.debug("method name : " + Arrays.toString(joinPoint.getArgs()));
		logger.debug("***************At last @After*******************");
	}

	@AfterReturning(pointcut = "execution(* com.stackroute.keepnote.controller.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint) {
		logger.info("==============@AfterReturning==============");
		logger.debug("method name : " + joinPoint.getSignature().getName());
		logger.debug("method name : " + Arrays.toString(joinPoint.getArgs()));
		logger.debug("****************At last @AfterReturning******************");
	}

	@AfterThrowing(pointcut = "execution(* com.stackroute.keepnote.controller.*.*(..))", throwing = "error")
	public void afterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.info("==============@AfterThrowing==============");
		logger.debug("method name : " + joinPoint.getSignature().getName());
		logger.debug("Exception : " + error);
		logger.debug("***************At last @AfterThrowing*******************");
	}
}