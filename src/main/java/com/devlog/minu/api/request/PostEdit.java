package com.devlog.minu.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostEdit {
  @NotBlank(message = "제목은 필수값입니다.")
  private final String title;
  @NotBlank(message = "내용은 필수값입니다.")
  private final String content;
}
