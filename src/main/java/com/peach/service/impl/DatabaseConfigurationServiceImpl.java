package com.peach.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.peach.common.Constants;
import com.peach.dao.DatabaseConfigurationRepository;
import com.peach.model.DatabaseConfiguration;
import com.peach.service.DatabaseConfigurationService;
import com.peach.vo.sql.SqlStatementVO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
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
  public List<Map<String, Object>> executeSql(@NonNull SqlStatementVO sqlStatementVO) {
    List<Map<String, Object>> list = Lists.newArrayList();
    DatabaseConfiguration databaseConfiguration = databaseConfigurationRepository.getOne(sqlStatementVO.getId());
    String url = String.format(Constants.DATA_BASE_URL, databaseConfiguration.getHost(),
        databaseConfiguration.getPort(), databaseConfiguration.getDatabaseName());
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      Connection connection =
          DriverManager.getConnection(url, databaseConfiguration.getUserName(), databaseConfiguration.getPassword());
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlStatementVO.getSql());
      List<String> columnList = Lists.newArrayListWithCapacity(resultSet.getMetaData().getColumnCount());
      for (int i = 1, j = resultSet.getMetaData().getColumnCount(); i <= j; i++) {
        columnList.add(resultSet.getMetaData().getColumnName(i));
      }
      while (resultSet.next()) {
        Map<String, Object> map = Maps.newHashMap();
        columnList.forEach(columnName -> {
          try {
            map.put(columnName, resultSet.getObject(columnName));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
        list.add(map);
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}
