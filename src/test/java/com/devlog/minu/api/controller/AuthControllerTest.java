package com.devlog.minu.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.config.AppConfig;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class AuthControllerTest {

  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AppConfig appConfig;

  @Autowired
  WebApplicationContext applicationContext;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.applicationContext)
        .apply(springSecurity())
        .build();
  }
  @AfterEach
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

  @DisplayName("로그인 테스트")
  @Test
  void test2() throws Exception {
    // given
    Signup signup = Signup.builder()
        .name("도현")
        .email("dh@test.com")
        .password("12345")
        .build();
    User user = signup.toEntity();
    userRepository.save(user);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
          .contentType(MediaType.APPLICATION_XML)
          .param("username", "dh@test.com")
          .param("password", "12345"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}