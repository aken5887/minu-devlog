package com.devlog.minu.api.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class PasswordEncoder {

  private static final SCryptPasswordEncoder encoder
      = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

  public static String encrypt(String rawPassword) {
    return encoder.encode(rawPassword);
  }

  public static boolean matches(String rawPassword, String encryptPassword) {
    return encoder.matches(rawPassword, encryptPassword);
  }

  public static SCryptPasswordEncoder getEncoder(){
    return encoder;
  }
}
