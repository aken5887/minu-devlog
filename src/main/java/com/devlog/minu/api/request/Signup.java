package com.devlog.minu.api.request;

import com.devlog.minu.api.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {
  private String email;
  private String name;
  private String password;

  public User toEntity(){
    return User.builder()
        .name(name)
        .email(email)
        .password(password)
        .build();
  }
}
