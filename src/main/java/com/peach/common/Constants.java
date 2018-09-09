package com.peach.common;

/**
 * @author 刘宇泽
 * @date 2018/7/5
 */
public interface Constants {


  int SUCCESS_CODE = 0;
  int ERROR_CODE = 1;
  String ERROR_MSG = "error";

  String DATA_BASE_URL = "jdbc:mysql://%s:%s/%s?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";

}
