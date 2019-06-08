package com.peach.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘宇泽
 * @date 2018-11-25
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

  @Pointcut("execution(public * com.peach.controller.*.*(..))")
  public void pointcut() {
  }

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Around("pointcut()")
  public Object exec(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String uri = httpServletRequest.getRequestURI();
    String method = httpServletRequest.getMethod();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      logger.info("process uri: {}, method :{}, args:{}, cost: {}", uri, method, proceedingJoinPoint.getArgs(), System.currentTimeMillis() - start);
    }
  }
}
