package com.devlog.minu.api.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostSearch {

  private static int MAX_SIZE = 300;

  @Builder.Default
  private Integer page = 1;
  @Builder.Default
  private Integer size = 10;

  public long getOffSet(){
    return (long) (Math.max(1, page) - 1) * Math.min(this.size, MAX_SIZE);
  }
}
