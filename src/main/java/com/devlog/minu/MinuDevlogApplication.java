package com.devlog.minu;

import com.devlog.minu.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class MinuDevlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(MinuDevlogApplication.class, args);
  }

}
