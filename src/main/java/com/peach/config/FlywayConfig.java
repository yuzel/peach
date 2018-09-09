package com.peach.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
@Configuration
public class FlywayConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public Flyway flyway(){
    Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource);
    flyway.setBaselineOnMigrate(true);
    flyway.setBaselineVersion(MigrationVersion.fromVersion("1.0"));
    flyway.repair();
    flyway.migrate();
    return flyway;
  }


}
