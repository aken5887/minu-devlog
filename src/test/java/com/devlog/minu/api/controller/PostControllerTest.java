package com.devlog.minu.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.request.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
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
    assertThat(postRepository.count()).isEqualTo(1L);
    assertThat(postRepository.findAll().get(0).getTitle()).isEqualTo("제목입니다1.");
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
  @DisplayName("/posts 요청으로 2페이지 데이터 여러건이 조회된다.")
  void getList() throws Exception {
    // given
    List<Post> postList = IntStream.range(1, 41)
            .mapToObj(i-> Post.builder()
                .title("title "+i)
                .content("content "+i)
                .build())
                .collect(Collectors.toList());
    postRepository.saveAll(postList);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=4000")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()",  is(40)))
        .andExpect(jsonPath("$[0].title").value("title 40"))
        .andExpect(jsonPath("$[0].content").value("content 40"));
  }

  @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
  @Test
  void request_null_page() throws Exception {
    // given
    List<Post> postList = IntStream.range(1, 41)
        .mapToObj(i-> Post.builder()
            .title("title "+i)
            .content("content "+i)
            .build())
        .collect(Collectors.toList());
    postRepository.saveAll(postList);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()",  is(10)))
        .andExpect(jsonPath("$[0].title").value("title 40"))
        .andExpect(jsonPath("$[0].content").value("content 40"));
  }

  @DisplayName("글 제목 및 내용 수정")
  @Test
  public void edit() throws Exception {
    // given
    Post post = new Post("제목", "내용");
    postRepository.save(post);

    PostEdit postEdit = PostEdit.builder()
        .title("김수영")
        .content("퍼레이드")
        .build();

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/posts/"+post.getId())
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postEdit)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("김수영"))
        .andExpect(jsonPath("$.content").value("퍼레이드"));
  }

  @DisplayName("/posts DELETE 요청 -> 게시글 삭제 테스트")
  @Test
  void delete() throws Exception {
    // given
    Post post = Post.builder()
        .title("보리")
        .content("웅녀")
        .build();
    postRepository.save(post);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    assertThat(postRepository.count()).isEqualTo(0L);
  }

  @DisplayName("존재하지 않는 게시글 조회")
  @Test
  void get_not_exists() throws Exception{
    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @DisplayName("존재하지 않는 게시글 수정")
  @Test
  void edit_not_exists() throws Exception {
    PostEdit postEdit = PostEdit
        .builder()
        .title("존제하지 않는 게시글")
        .content("수정하는 테스트")
        .build();

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/posts/1")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postEdit)))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @DisplayName("게시글 작성시 제목에 '테스트'는 포함될 수 없다.")
  @Test
  void create_validate() throws Exception {
      // given
      PostCreate postCreate = PostCreate.builder()
          .title("테스트 제목")
          .content("테스트 내용")
          .build();

      // expected
    this.mockMvc.perform(MockMvcRequestBuilders.post("/posts")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postCreate)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.validation.title").value("제목엔 '테스트'가 포함될 수 없습니다."));
  }
}