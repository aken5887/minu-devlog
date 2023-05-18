package com.devlog.minu.api.exception;

public class UnAuthorized extends CommonException{

  private static final String MESSAGE = "권한이 없습니다.";

  public UnAuthorized() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 401;
  }
}
