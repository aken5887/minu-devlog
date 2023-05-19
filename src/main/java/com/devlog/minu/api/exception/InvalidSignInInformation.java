package com.devlog.minu.api.exception;

public class InvalidSignInInformation extends CommonException{

  private static final String MESSAGE = "아이디/비밀번호가 일치하지 않습니다.";

  public InvalidSignInInformation() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
