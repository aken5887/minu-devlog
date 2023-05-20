package com.devlog.minu.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.domain.Session;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void clean(){
    userRepository.deleteAll();
  }

  @DisplayName("로그인 성공")
  @Test
  void test() throws Exception {
    // given
    User user = User.builder()
        .name("도현")
        .email("d@test.com")
        .password("1234")
        .build();

    userRepository.save(user);

    Login login = Login.builder()
        .email("d@test.com")
        .password("1234")
        .build();

    String json = objectMapper.writeValueAsString(login);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @DisplayName("로그인 성공 후 세션 1개 생성")
  @Transactional
  @Test
  void test2() throws Exception {
    // given
    User user = User.builder()
        .name("도현")
        .email("d@test.com")
        .password("1234")
        .build();

    userRepository.save(user);

    Login login = Login.builder()
        .email("d@test.com")
        .password("1234")
        .build();

    String json = objectMapper.writeValueAsString(login);

    // when
    this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andDo(print())
        .andExpect(status().isOk());

    // then
    assertThat(user.getSessions().size()).isGreaterThan(0);
  }

  @DisplayName("로그인 성공 후 세션 응답이 존재한다.")
  @Test
  void test3() throws Exception {
    // given
    User user = User.builder()
        .name("도현")
        .email("d@test.com")
        .password("1234")
        .build();

    userRepository.save(user);

    Login login = Login.builder()
        .email("d@test.com")
        .password("1234")
        .build();

    String json = objectMapper.writeValueAsString(login);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", notNullValue()));
  }

  @DisplayName("로그인 후 권한이 필요한 페이지 접속한다. GET '/post/auth'")
  @Test
  void test4() throws Exception{
    // given
    User user = User.builder()
        .name("최수영")
        .email("sooyoung@test.com")
        .password("12345")
        .build();

    Session newSession = user.addSession(); // 로그인 후 세션 생성
    userRepository.save(user);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .header("authorization", newSession.getAccessToken()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("1"));
  }

  @DisplayName("로그인 후 검증되지 않은 세션값으로 권한이 필요한 페이지에 접속할 수 없다.")
  @Test
  void test5() throws Exception{
    // given
    User user = User.builder()
        .name("최수영")
        .email("sooyoung@test.com")
        .password("12345")
        .build();

    Session newSession = user.addSession(); // 로그인 후 세션 생성
    userRepository.save(user);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .header("authorization", newSession.getAccessToken() + "-1"))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }
}