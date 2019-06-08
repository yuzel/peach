package com.peach.service;

import com.peach.model.DatabaseConfiguration;
import com.peach.vo.sql.SqlMetaData;
import com.peach.vo.sql.SqlStatement;

import java.util.List;
import java.util.Map;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
public interface DatabaseConfigurationService {

  /**
   * save
   *
   * @param databaseConfiguration
   * @return
   */
  DatabaseConfiguration save(DatabaseConfiguration databaseConfiguration);

  /**
   * update
   *
   * @param id
   * @param databaseConfiguration
   * @return
   */
  DatabaseConfiguration update(Integer id, DatabaseConfiguration databaseConfiguration);

  /**
   * info
   *
   * @param id
   * @return
   */
  DatabaseConfiguration getDatabaseConfigurationInfo(Integer id);

  /**
   * 获取元数据
   *
   * @param id
   * @return
   */
  Map<String, List<SqlMetaData>> getDatabaseConfigurationMetadata(Integer id);

  /**
   * delete
   *
   * @param id
   * @return
   */
  void deleteDatabaseConfigurationInfo(Integer id);

  /**
   * list
   *
   * @return
   */
  List<DatabaseConfiguration> listDatabaseConfiguration();

  /**
   * sql execute
   *
   * @param sqlStatement
   */
  Object executeSql(SqlStatement sqlStatement);

}
