package com.peach.vo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.peach.common.Constants.ERROR_CODE;
import static com.peach.common.Constants.SUCCESS_CODE;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg.ERROR_MSG;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

  @NotNull(message = "Result code must not be null.")
  public Integer code;
  public String msg;
  public Object data;

  public static Result fail() {
    return new Result(ERROR_CODE);
  }

  public static Result fail(String msg) {
    return new Result(ERROR_CODE, msg);
  }

  public static Result succ() {
    return new Result(SUCCESS_CODE);
  }

  public static Result succ(Object data) {
    return new Result(SUCCESS_CODE, null, data);
  }

  public static Result error() {
    return error(ERROR_MSG);
  }

  public static Result error(String msg) {
    Result result = new Result();
    result.setMsg(msg);
    result.setCode(ERROR_CODE);
    return result;
  }

  public Result(Integer code) {
    this(code, null, null);
  }

  public Result(Integer code, String msg) {
    this(code, msg, null);
  }

}
