package com.devlog.minu.api.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  PostRepository postRepository;

  @BeforeEach
  void clean(){
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("/posts를 JSON객체로 POST 요청하면 {}를 리턴한다.")
  void post() throws Exception {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("제목입니다.")
        .content("이것은 내용입니다.")
        .build();
    String json = objectMapper.writeValueAsString(postCreate);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/posts")
            .contentType(APPLICATION_JSON)
            .content(json))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  @DisplayName("/post를 POST 요청하면 title 값은 필수값이다.")
  void post_title_required() throws Exception{
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("")
        .content("이것은 내용입니다.")
        .build();
    String json = objectMapper.writeValueAsString(postCreate);

    // when
    this.mockMvc.perform(MockMvcRequestBuilders.post("/posts")
        .contentType(APPLICATION_JSON)
        .content(json))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.title").value("제목은 필수입니다."));
  }

  @Test
  @DisplayName("/post를 요청하면 데이터가 저장된다.")
  void post_save() throws Exception {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("제목입니다1.")
        .content("이것은 내용입니다.")
        .build();
    String json = objectMapper.writeValueAsString(postCreate);

    // when
    this.mockMvc.perform(MockMvcRequestBuilders.post("/posts")
        .contentType(APPLICATION_JSON)
        .content(json))
        .andDo(print())
        .andExpect(status().isOk());

    // then
    Assertions.assertThat(postRepository.count()).isEqualTo(1L);
    Assertions.assertThat(postRepository.findAll().get(0).getTitle()).isEqualTo("제목입니다1.");
  }

  @Test
  @DisplayName("/posts 단건 데이터를 조회 한다.")
  void get() throws Exception{
    //given
    Post requestPost = Post.builder()
        .title("foofoofoofoofoo555")
        .content("bar")
        .build();
    postRepository.save(requestPost);

    //expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", requestPost.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("foofoofoof"))
        .andExpect(jsonPath("$.content").value("bar"));
  }

  @Test
  @DisplayName("/posts 요청으로 여러건의 데이터가 조회된다.")
  void getList() throws Exception {
    // given
    postRepository.saveAll(List.of(
        Post.builder()
            .content("content_1")
            .title("title_1")
            .build(),
        Post.builder()
            .title("title_2")
            .content("content_2")
            .build()
    ));
    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()",  is(2)))
        .andExpect(jsonPath("$[0].title").value("title_1"))
        .andExpect(jsonPath("$[0].content").value("content_1"));
  }
}