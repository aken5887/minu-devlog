package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import java.time.LocalDateTime;
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
    User findUser = userService.login(login);
    System.out.println("user : " + findUser.getCreatedAt());
    assertThat(findUser.getName()).isEqualTo("도현");
    assertThat(findUser.getCreatedAt()).isBefore(LocalDateTime.now());
  }
}