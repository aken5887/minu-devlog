package com.devlog.minu.api.exception;

public class AlreadyExistEmailException extends CommonException{

  private static final String MESSAGE = "이미 존재하는 이메일 입니다.";

  public AlreadyExistEmailException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
