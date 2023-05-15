package com.devlog.minu.api.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException{

  private final Map<String, Object> validation = new HashMap<>();

  public CommonException(String message) {
    super(message);
  }

  public abstract int getStatusCode();

  public void addValidation(String field, String message){
    this.validation.put(field, message);
  }
}
