package com.devlog.minu.api.domain;

import com.devlog.minu.api.request.PostCreate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity(name="TB_POST")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @Lob
  private String content;

  @Builder
  public Post(String title, String content){
    this.title = title;
    this.content = content;
  }

  public Post toEntity(PostCreate postCreate){
    this.title = postCreate.getTitle();
    this.content = postCreate.getContent();
    return this;
  }

  public PostEditor.PostEditorBuilder toEditor(){
    PostEditor.PostEditorBuilder editorBuilder
        = PostEditor.builder()
        .title(this.title)
        .content(this.content);
    return editorBuilder;
  }

  public void edit(PostEditor postEditor){
    this.title = postEditor.getTitle();
    this.content = postEditor.getContent();
  }
}
