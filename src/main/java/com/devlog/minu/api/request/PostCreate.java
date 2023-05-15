package com.devlog.minu.api.request;

import com.devlog.minu.api.exception.InvalidRequest;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreate {
  @NotBlank(message="제목은 필수입니다.")
  private final String title;
  @NotBlank(message="내용은 필수입니다.")
  private final String content;

  @Builder
  public PostCreate(String title, String content){
    this.title = title;
    this.content = content;
  }

  public void validate() {
    if(this.title.contains("테스트")){
      throw InvalidRequest.builder()
          .field("title")
          .fieldMessage("제목엔 '테스트'가 포함될 수 없습니다.")
          .build();
    }
  }
}
