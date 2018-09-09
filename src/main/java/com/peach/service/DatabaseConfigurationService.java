package com.peach.service;

import com.peach.model.DatabaseConfiguration;
import com.peach.vo.sql.SqlStatementVO;

import java.util.List;
import java.util.Map;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
public interface DatabaseConfigurationService {

  /**
   * save
   * @param databaseConfiguration
   * @return
   */
  DatabaseConfiguration save(DatabaseConfiguration databaseConfiguration);

  /**
   * update
   * @param id
   * @param databaseConfiguration
   * @return
   */
  DatabaseConfiguration update(Integer id,DatabaseConfiguration databaseConfiguration);

  /**
   * info
   * @param id
   * @return
   */
  DatabaseConfiguration getDatabaseConfigurationInfo(Integer id);

  /**
   * list
   * @return
   */
  List<DatabaseConfiguration> listDatabaseConfiguration();

  /**
   * sql execute
   * @param sqlStatementVO
   */
  List<Map<String,Object>> executeSql(SqlStatementVO sqlStatementVO);

}
