package com.devlog.minu.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
  private final AppConfig appConfig;
}
