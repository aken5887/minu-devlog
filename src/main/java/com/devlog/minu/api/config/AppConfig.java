package com.devlog.minu.api.config;

import java.util.Base64;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "devlog")
public class AppConfig {
  private byte[] jwtKey;

  public void setJwtKey(String jwtKey){
    this.jwtKey = Base64.getDecoder().decode(jwtKey);
  }

  public byte[] getJwtKey(){
    return this.jwtKey;
  }
}
