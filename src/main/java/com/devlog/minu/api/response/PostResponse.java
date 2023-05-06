package com.devlog.minu.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

  private final Long id;
  private final String title;
  private final String content;

  @Builder
  public PostResponse(Long id, String title, String content) {
    this.id = id;
    this.title = title.substring(0, Math.min(10, title.length())); // client 요구사항
    this.content = content;
  }
}
