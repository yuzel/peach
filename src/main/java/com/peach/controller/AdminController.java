package com.peach.controller;

import com.peach.model.DatabaseConfiguration;
import com.peach.service.DatabaseConfigurationService;
import com.peach.vo.common.Result;
import com.peach.vo.sql.SqlStatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 刘宇泽
 * @date 2018/7/5
 */
@RestController
@RequestMapping("admin_api/v1/")
public class AdminController {

  @Autowired
  private DatabaseConfigurationService databaseConfigurationService;

  @PostMapping("database_configuration")
  public Result saveDatabaseConfiguration(@Valid @RequestBody DatabaseConfiguration databaseConfiguration, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    DatabaseConfiguration result = databaseConfigurationService.save(databaseConfiguration);
    return Result.succ(result);
  }

  @PutMapping("database_configuration/{id}")
  public Result updateDatabaseConfiguration(@PathVariable("id") Integer id,
                                            @Valid @RequestBody DatabaseConfiguration databaseConfiguration, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    DatabaseConfiguration result = databaseConfigurationService.update(id, databaseConfiguration);
    return Result.succ(result);
  }

  @GetMapping("database_configuration/{id}")
  public Result getDatabaseConfiguration(@PathVariable("id") Integer id) {
    DatabaseConfiguration result = databaseConfigurationService.getDatabaseConfigurationInfo(id);
    return Result.succ(result);
  }

  @GetMapping("database_configurations")
  public Result listDatabaseConfiguration() {
    List<DatabaseConfiguration> databaseConfigurations = databaseConfigurationService.listDatabaseConfiguration();
    return Result.succ(databaseConfigurations);
  }

  @PostMapping("database_configurations/execute")
  public Result executeSql(@Valid @RequestBody SqlStatementVO sqlStatementVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    List<Map<String, Object>> list = databaseConfigurationService.executeSql(sqlStatementVO);
    return Result.succ(list);
  }

}
