package com.devlog.minu.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class PostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("/posts를 GET 요청하면 Hello World를 리턴한다.")
  void get() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Hello World"));
  }
}