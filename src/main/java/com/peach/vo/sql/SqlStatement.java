package com.peach.vo.sql;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 刘宇泽
 * @date 2018/9/9
 */
@Data
public class SqlStatement {

  @NotBlank(message = "sql must not null.")
  private String sql;

  @NotNull(message = "id must not null.")
  private Integer id;

}
