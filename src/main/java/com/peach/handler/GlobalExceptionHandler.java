package com.peach.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.peach.vo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author 刘宇泽
 * @date 2018/9/10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 警告级别的异常
   *
   * @param e
   * @param request
   * @return Result
   */
  @ExceptionHandler({
      ClientAbortException.class,
      MethodArgumentTypeMismatchException.class,
      HttpRequestMethodNotSupportedException.class,
      ServletRequestBindingException.class,
      JsonMappingException.class,
      IllegalArgumentException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Result handleWarningLevelException(Exception e, HttpServletRequest request) {
    String method = null;
    String url = null;
    if (request != null) {
      method = request.getMethod();
      url = request.getRequestURL().toString();
    }
    logger.warn("{} occurred, method: {}, url: {}.", e.getClass().getSimpleName(), method, url, e);
    return Result.fail(e.getMessage());
  }

  /**
   * 其他异常，视为错误级别
   *
   * @param e
   * @param request
   * @return Result
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result handleErrorLevelException(Exception e, HttpServletRequest request) {
    String method = null;
    String url = null;
    if (request != null) {
      method = request.getMethod();
      url = request.getRequestURL().toString();
    }
    logger.error("Error occurred, method: {}, url: {}.", method, url, e);
    return Result.fail(e.getMessage());
  }

}
