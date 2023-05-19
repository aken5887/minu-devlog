package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @BeforeEach
  void cleanup(){
    userRepository.deleteAll();
  }

  @DisplayName("User가 저장된다.")
  @Test
  void test1() {
    // given
    User user = User.builder()
        .email("dohyun1234@test.com")
        .name("도현")
        .password("1234")
        .build();

    userRepository.save(user);

    Login login = Login.builder()
        .email("dohyun1234@test.com")
        .password("1234")
        .build();

    // when
    assertThat(userRepository.count()).isEqualTo(1);
  }
}