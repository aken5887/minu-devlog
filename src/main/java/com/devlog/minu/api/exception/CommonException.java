package com.devlog.minu.api.exception;

public abstract class CommonException extends RuntimeException{

  public CommonException() {
  }

  public CommonException(String message) {
    super(message);
  }

  public abstract int getStatusCode();
}
