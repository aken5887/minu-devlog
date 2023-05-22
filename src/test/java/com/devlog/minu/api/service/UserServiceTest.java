package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devlog.minu.api.crypto.PasswordEncoder;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.exception.AlreadyExistEmailException;
import com.devlog.minu.api.exception.InvalidSignInInformation;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
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

    Login login = Login.builder()
        .email("dohyun1234@test.com")
        .password("1234")
        .build();

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
    assertThat(savedUser.getEmail()).isEqualTo(signup.getEmail());
    assertThat(savedUser.getPassword()).isNotEqualTo(signup.getPassword());
    assertThat(savedUser.getName()).isEqualTo(signup.getName());
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

  @DisplayName("로그인성공")
  @Test
  void test4() {
    // given
    User user = User.builder()
            .name("핑크")
            .email("pink@test.com")
            .password(PasswordEncoder.encrypt("123451"))
            .build();
    userRepository.save(user);

    Login login = Login.builder()
        .email("pink@test.com")
        .password("123451")
        .build();

    // when
    Long userId = userService.login(login);

    // expected
    assertThat(userId).isEqualTo(user.getId());
  }

  @DisplayName("로그인 비밀번호 틀린 경우 InvalidSigninInformation 익셉션을 발생 시킨다.")
  @Test
  void test5() {
    // given
    User user = User.builder()
        .name("핑크")
        .email("pink@test.com")
        .password(PasswordEncoder.encrypt("123451"))
        .build();
    userRepository.save(user);

    Login login = Login.builder()
        .email("pink@test.com")
        .password("123451-1")
        .build();

    // expected
    Assertions.assertThrows(InvalidSignInInformation.class, () -> {
      userService.login(login);
    });
  }
}