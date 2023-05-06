package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.domain.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

  @Autowired
  PostService postService;

  @Autowired
  PostRepository postRepository;

  @BeforeEach
  void clean(){
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("Post 엔티티를 저장한다.")
  void write(){
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("foo")
        .content("bar")
        .build();

    // when
    postService.write(postCreate);

    // then
    assertThat(postRepository.count()).isEqualTo(1L);
  }

  @Test
  @DisplayName("저장된 Post 엔티티를 조회한다.")
  void get(){
    // given
    Post savedPost = Post.builder()
        .title("title")
        .content("content")
        .build();

    postRepository.save(savedPost);

    // when
    Post post = postService.get(savedPost.getId());

    // then
    assertThat(post).isNotNull();
    assertThat(post.getTitle()).isEqualTo(savedPost.getTitle());
    assertThat(post.getContent()).isEqualTo(savedPost.getContent());
  }

  @Test
  @DisplayName("없는 게시글을 조회할 때 익셉션을 발생시킨다.")
  void get_exception(){
    // given
    // when
    // then
    assertThatThrownBy(
        () -> postService.get(1L)).isInstanceOf(IllegalArgumentException.class);
  }
}