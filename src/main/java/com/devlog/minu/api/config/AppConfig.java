package com.devlog.minu.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "custom.data.human")
public class AppConfig {
  private String name;
  private int age;
  private String address;
}
