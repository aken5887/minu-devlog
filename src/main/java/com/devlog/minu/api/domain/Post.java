package com.devlog.minu.api.domain;

import com.devlog.minu.api.request.PostCreate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;

@Getter
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String content;

  public Post toEntity(PostCreate postCreate){
    this.title = postCreate.getTitle();
    this.content = postCreate.getContent();
    return this;
  }
}
