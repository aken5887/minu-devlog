package com.devlog.minu.api.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class JwtKey {

  private static final Key key;

  private static final String strKey;

  static {
    key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    byte[] encodedKey = key.getEncoded();
    strKey = Base64.getEncoder().encodeToString(encodedKey);
  }

  public static String getStrKey(){
    return strKey;
  }

  public static Key getKey(){
    return key;
  }
}
