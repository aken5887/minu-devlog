package com.devlog.minu.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class PostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("/posts를 GET 요청하면 Hello World를 리턴한다.")
  void get() throws Exception {
    // expected
    mockMvc.perform(MockMvcRequestBuilders.get("/posts")) // content-type : application/json
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Hello World"));
  }

  @Test
  @DisplayName("/posts를 JSON객체로 POST 요청하면 Hello World를 리턴한다.")
  void post() throws Exception {
    // given
    String postCreate = "{\"title\" : \"제목입니다.\", \"content\" : \"이것은 내용입니다.\"}";

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postCreate))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{}"));
  }

  @Test
  @DisplayName("/post를 POST 요청하면 title 값은 필수값이다.")
  void post_title_required() throws Exception{
    this.mockMvc.perform(MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\" : \"\", \"content\" : \"이것은 내용입니다.\"}"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.title").value("제목은 필수입니다."));
  }

}