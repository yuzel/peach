package com.peach.util;

import com.google.common.collect.Maps;
import com.peach.common.Constants;
import com.peach.model.DatabaseConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 刘宇泽
 * @date 2018/9/10
 */
public class DateSourceFactory {

  private static ConcurrentMap<String, DataSource> dataSourceMap = Maps.newConcurrentMap();

  public static DataSource getDateSource(DatabaseConfiguration databaseConfiguration) {
    String url = String.format(Constants.DATA_BASE_URL, databaseConfiguration.getHost(),
        databaseConfiguration.getPort(), databaseConfiguration.getDatabaseName());
    if(dataSourceMap.containsKey(url)){
      return dataSourceMap.get(url);
    }
    HikariConfig config = new HikariConfig();
    config.setMinimumIdle(1);
    config.setMaximumPoolSize(10);
    config.setConnectionTestQuery("SELECT 1");
    config.setDriverClassName("com.mysql.jdbc.Driver");
    config.setJdbcUrl(url);
    config.setUsername(databaseConfiguration.getUserName());
    config.setPassword(databaseConfiguration.getPassword());
    DataSource dataSource = new HikariDataSource(config);
    dataSourceMap.putIfAbsent(url,dataSource);
    return dataSource;
  }

}
