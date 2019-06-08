package com.peach.vo.sql;

import lombok.Data;

/**
 * @author 刘宇泽
 * @date 2019-06-07
 */
@Data
public class SqlMetaData {

  private String tableName;

  private String columnName;

  private String columnType;

}
