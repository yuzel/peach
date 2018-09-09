package com.peach.model;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
@Data
@Entity
@DynamicInsert
@Table(name = "database_configurations")
@EntityListeners(AuditingEntityListener.class)
public class DatabaseConfiguration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "host must not null.")
  private String host;

  @NotBlank(message = "port must not null.")
  private String port;

  @NotBlank(message = "database_name must not null.")
  private String databaseName;

  @NotBlank(message = "user_name must not null.")
  private String userName;

  private String password;

  @NotNull(message = "type must not null.")
  private Integer type;

  @CreatedDate
  @Column(updatable = false)
  private Date createdDate;

  @LastModifiedDate
  private Date updatedDate;


}
