package com.devlog.minu.api.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class PostCreate {
  private String title;
  private String content;
}
