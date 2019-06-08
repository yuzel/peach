package com.peach.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.peach.dao.DatabaseConfigurationRepository;
import com.peach.model.DatabaseConfiguration;
import com.peach.service.DatabaseConfigurationService;
import com.peach.util.DateSourceFactory;
import com.peach.vo.sql.SqlMetaData;
import com.peach.vo.sql.SqlStatement;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
@Slf4j
@Service
public class DatabaseConfigurationServiceImpl implements DatabaseConfigurationService {

  @Autowired
  private DatabaseConfigurationRepository databaseConfigurationRepository;

  @Override
  public DatabaseConfiguration save(@NonNull DatabaseConfiguration databaseConfiguration) {
    return databaseConfigurationRepository.save(databaseConfiguration);
  }

  @Override
  public DatabaseConfiguration update(@NonNull Integer id, @NonNull DatabaseConfiguration databaseConfiguration) {
    databaseConfiguration.setId(id);
    return databaseConfigurationRepository.save(databaseConfiguration);
  }

  @Override
  public DatabaseConfiguration getDatabaseConfigurationInfo(@NonNull Integer id) {
    return databaseConfigurationRepository.findById(id).orElse(null);
  }

  @Override
  public Map<String, List<SqlMetaData>> getDatabaseConfigurationMetadata(Integer id) {
    DatabaseConfiguration databaseConfiguration = databaseConfigurationRepository.findById(id).orElse(null);
    Assert.notNull(databaseConfiguration, "Please set the database configuration first.");
    DataSource dataSource;
    try {
      dataSource = DateSourceFactory.getDateSource(databaseConfiguration);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to connect to the database, please check the database configuration.");
    }
    List<SqlMetaData> sqlMetaDataList = null;
    try (
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()
    ) {
      String query = String.format("select TABLE_NAME, COLUMN_NAME, COLUMN_TYPE from information_schema.COLUMNS where TABLE_SCHEMA = '%s'",
          databaseConfiguration.getDatabaseName());
      ResultSet resultSet = statement.executeQuery(query);
      sqlMetaDataList = Lists.newArrayListWithCapacity(resultSet.getMetaData().getColumnCount());
      while (resultSet.next()) {
        SqlMetaData sqlMetaData = new SqlMetaData();
        sqlMetaData.setTableName(resultSet.getString("TABLE_NAME"));
        sqlMetaData.setColumnName(resultSet.getString("COLUMN_NAME"));
        sqlMetaData.setColumnType(resultSet.getString("COLUMN_TYPE"));
        sqlMetaDataList.add(sqlMetaData);
      }
    } catch (Exception e) {
      logger.error("error.", e);
    }
    return Optional.ofNullable(sqlMetaDataList)
        .orElse(Collections.emptyList())
        .stream()
        .collect(Collectors.groupingBy(SqlMetaData::getTableName));
  }

  @Override
  public void deleteDatabaseConfigurationInfo(Integer id) {
    DatabaseConfiguration databaseConfiguration = databaseConfigurationRepository.findById(id).orElse(null);
    Assert.notNull(databaseConfiguration, "Please set the database configuration first.");
    DateSourceFactory.removeDateSource(databaseConfiguration);
  }

  @Override
  public List<DatabaseConfiguration> listDatabaseConfiguration() {
    return databaseConfigurationRepository.findAll();
  }

  @Override
  public Object executeSql(@NonNull SqlStatement sqlStatement) {
    DatabaseConfiguration databaseConfiguration = databaseConfigurationRepository.findById(sqlStatement.getId()).orElse(null);
    Assert.notNull(databaseConfiguration, "Please set the database configuration first.");
    DataSource dataSource;
    try {
      dataSource = DateSourceFactory.getDateSource(databaseConfiguration);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to connect to the database, please check the database configuration.");
    }
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {
      if (StringUtils.startsWithIgnoreCase(sqlStatement.getSql().trim(), "SELECT")) {
        return this.getExecuteQuery(sqlStatement, statement);
      } else {
        return statement.executeUpdate(sqlStatement.getSql());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(String.format("Unable to execute sql, please check if sql is feasible.error:%s", e.getMessage()));
    }
  }

  private List<Map<String, Object>> getExecuteQuery(@NonNull SqlStatement sqlStatement, Statement statement) throws SQLException {
    List<Map<String, Object>> list = Lists.newArrayList();
    ResultSet resultSet = statement.executeQuery(sqlStatement.getSql());
    List<String> columnList = Lists.newArrayListWithCapacity(resultSet.getMetaData().getColumnCount());
    for (int i = 1, j = resultSet.getMetaData().getColumnCount(); i <= j; i++) {
      columnList.add(resultSet.getMetaData().getColumnName(i));
    }
    while (resultSet.next()) {
      Map<String, Object> map = Maps.newHashMapWithExpectedSize(resultSet.getMetaData().getColumnCount());
      columnList.forEach(columnName -> {
        try {
          map.put(columnName, resultSet.getObject(columnName));
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });
      list.add(map);
    }
    return list;
  }
}
