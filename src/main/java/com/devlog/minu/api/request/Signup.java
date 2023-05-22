package com.devlog.minu.api.request;

import com.devlog.minu.api.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Data
@Builder
public class Signup {
  private String email;
  private String name;
  private String password;

  public User toEntity(){
    SCryptPasswordEncoder encoder =
        new SCryptPasswordEncoder(16, 8, 1, 32, 64);
    String encodedPassword = encoder.encode(this.password);

    return User.builder()
        .name(name)
        .email(email)
        .password(encodedPassword)
        .build();
  }
}
