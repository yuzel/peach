package com.peach.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.peach.dao.DatabaseConfigurationRepository;
import com.peach.model.DatabaseConfiguration;
import com.peach.service.DatabaseConfigurationService;
import com.peach.util.DateSourceFactory;
import com.peach.vo.sql.SqlStatementVO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
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
  public List<DatabaseConfiguration> listDatabaseConfiguration() {
    return databaseConfigurationRepository.findAll();
  }

  @Override
  public Object executeSql(@NonNull SqlStatementVO sqlStatementVO) {
    DatabaseConfiguration databaseConfiguration = databaseConfigurationRepository.findById(sqlStatementVO.getId()).orElse(null);
    Assert.notNull(databaseConfiguration, "Please set the database configuration first.");
    DataSource dataSource = null;
    try {
      dataSource = DateSourceFactory.getDateSource(databaseConfiguration);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to connect to the database, please check the database configuration.");
    }
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {
      if (StringUtils.startsWithIgnoreCase(sqlStatementVO.getSql().trim(), "SELECT")) {
        return this.getExcuteQuery(sqlStatementVO, statement);
      } else {
        return statement.executeUpdate(sqlStatementVO.getSql());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(String.format("Unable to execute sql, please check if sql is feasible.error:%s", e.getMessage()));
    }
  }

  private List<Map<String, Object>> getExcuteQuery(@NonNull SqlStatementVO sqlStatementVO, Statement statement) throws SQLException {
    List<Map<String, Object>> list = Lists.newArrayList();
    ResultSet resultSet = statement.executeQuery(sqlStatementVO.getSql());
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
