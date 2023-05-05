package com.devlog.minu.api.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class PostCreate {
  @NotBlank(message="제목은 필수입니다.")
  private String title;
  @NotBlank(message="내용은 필수입니다.")
  private String content;
}
