package com.devlog.minu.api.exception;

public class PostNotFound extends RuntimeException {

  private static final String MESSAGE = "존재하지 않는 게시글입니다.";

  public PostNotFound() {
    super(MESSAGE);
  }
}
