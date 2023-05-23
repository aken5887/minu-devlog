package com.devlog.minu.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.config.AppConfig;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AppConfig appConfig;

  @BeforeEach
  void clean(){
    userRepository.deleteAll();
  }

  @DisplayName("회원가입")
  @Test
  public void test6() throws Exception {
    // given
    Signup signup = Signup.builder()
        .name("도현")
        .email("dh@test.com")
        .password("12345")
        .build();

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signup)))
        .andDo(print())
        .andExpect(status().isOk());

  }
}