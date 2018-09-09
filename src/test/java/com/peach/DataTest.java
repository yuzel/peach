package com.peach;

import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author 刘宇泽
 * @date 2018/8/30
 */
public class DataTest {

  public static void main(String[] args) throws Exception{
    System.out.println(System.getProperties());
    String url = "jdbc:mysql://localhost:3306/jarvis?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    String user = "develop";
    String password = "develop";
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url,user,password);
    Statement statement = connection.createStatement();
    List<String> columnList = Lists.newArrayList();
    ResultSet resultSet = statement.executeQuery("select * from manual_pushes");
    Map<String,List<Object>> resultMap = Maps.newHashMap();
    for (int i = 1,j=resultSet.getMetaData().getColumnCount(); i <= j ; i++) {
      columnList.add(resultSet.getMetaData().getColumnName(i));
    }
    while (resultSet.next()){
      columnList.forEach(columnName-> {
        try {
          List<Object> list = resultMap.get(columnName);
          if(list == null){
            list = Lists.newArrayList();
          }
          list.add(resultSet.getObject(columnName));
          resultMap.put(columnName,list);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });
    }
    System.out.println(resultMap);
    resultSet.close();
    statement.close();
    connection.close();
  }
}
