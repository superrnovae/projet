package com.example.web.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(AsyncExceptionHandler.class);
	
	@Override
    public void handleUncaughtException(
      Throwable throwable, Method method, Object... obj) {
 
		logger.error("Exception message - '{}'", throwable.getMessage());
        logger.error("Method name - '{}' ", method.getName());
        for (Object param : obj) {
            logger.error("Parameter value - '{}' ", param);
        }
    }
}
