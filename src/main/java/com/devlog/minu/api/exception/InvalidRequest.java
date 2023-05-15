package com.devlog.minu.api.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InvalidRequest extends CommonException {

  private static final String MESSAGE = "잘못된 요청입니다.";
  private String field;
  private String fieldMessage;

  @Builder
  public InvalidRequest(String field, String fieldMessage){
    super(MESSAGE);
    addValidation(field, fieldMessage);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
