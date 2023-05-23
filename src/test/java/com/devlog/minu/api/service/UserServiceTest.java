package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devlog.minu.api.crypto.PasswordEncoder;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.exception.AlreadyExistEmailException;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Signup;
import org.junit.jupiter.api.Assertions;
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

    // when
    assertThat(userRepository.count()).isEqualTo(1);
  }

  @DisplayName("회원가입 성공")
  @Test
  void test2(){
    // given
    Signup signup = Signup.builder()
        .email("dh@test.com")
        .name("도현")
        .password("12456")
        .build();
    // when
    userService.signup(signup);
    // then
    assertThat(userRepository.count()).isEqualTo(1);

    User savedUser = userRepository.findUserByEmail(signup.getEmail())
        .orElseThrow(() -> new RuntimeException());
    System.out.println("user password : " + savedUser.getPassword());
    assertThat("dh@test.com").isEqualTo(savedUser.getEmail());
    assertThat(PasswordEncoder.matches("12456", savedUser.getPassword()));
    assertThat("도현").isEqualTo(savedUser.getName());
  }
  @DisplayName("중복된 이메일로 회원가입시 AlreadyExistEmailException 발생한다.")
  @Test
  void test3() {
    // given
    User user = User.builder()
        .name("기현")
        .email("dh@test.com")
        .password("1234")
        .build();
    userRepository.save(user);

    Signup signup = Signup.builder()
        .email("dh@test.com")
        .name("도현")
        .password("12456")
        .build();
    // expected
    Assertions.assertThrows(AlreadyExistEmailException.class, () -> userService.signup(signup));
  }
}