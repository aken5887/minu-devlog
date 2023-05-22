package com.devlog.minu.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.config.AppConfig;
import com.devlog.minu.api.domain.Session;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.SessionRepository;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

  @Autowired
  SessionRepository sessionRepository;

  @Autowired
  AppConfig appConfig;

  @BeforeEach
  void clean(){
    userRepository.deleteAll();
  }

  @AfterEach
  void cleanUp(){
    sessionRepository.deleteAll();
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
        .contentType(APPLICATION_JSON)
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
            .contentType(APPLICATION_JSON)
            .content(json))
        .andDo(print())
        .andExpect(status().isOk());

    // then
    assertThat(user.getSessions().size()).isGreaterThan(0);
  }

  @DisplayName("로그인 성공 후 쿠키에 세션이 존재한다.")
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
            .contentType(APPLICATION_JSON)
            .content(json))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.cookie().exists("SESSION"));
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
    userRepository.save(user);

    LocalDateTime exprDateTime = LocalDateTime.now().plusMonths(1L);

    SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());
    String jwt = Jwts.builder()
        .setSubject(String.valueOf(user.getId()))
        .signWith(secretKey)
        .setExpiration(Date.valueOf(exprDateTime.toLocalDate()))
        .compact();

    Cookie sessionCookie = new Cookie("SESSION",jwt);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/auth")
        .contentType(APPLICATION_JSON)
        .cookie(sessionCookie))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(user.getId())));
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
    Cookie sessionCookie = new Cookie("SESSION", newSession.getAccessToken()+"-1");

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/auth")
            .contentType(APPLICATION_JSON)
            .cookie(sessionCookie))
        .andDo(print())
        .andExpect(status().isUnauthorized());
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