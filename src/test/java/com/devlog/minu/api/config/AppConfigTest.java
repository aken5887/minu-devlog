package com.devlog.minu.api.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppConfigTest {

  @Autowired
  AppConfig appConfig;

  @DisplayName("application.yml 프로피티를 읽어온다.")
  @Test
  void test(){
    //expected
    assertThat(appConfig.getName()).isNotEmpty();
    assertDoesNotThrow(() -> {
      appConfig.getAge();
    });
    assertThat(appConfig.getAddress()).isNotEmpty();
    System.out.println(appConfig.toString());
  }
}