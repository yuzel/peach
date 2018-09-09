package com.peach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class PeachApplication {

  public static void main(String[] args) {
    SpringApplication.run(PeachApplication.class, args);
  }
}
