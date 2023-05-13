package com.devlog.minu.api.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostEdit {
  private final String title;
  private final String content;
}
