package com.devlog.minu.api.service;

import com.devlog.minu.api.domain.Session;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.exception.InvalidSignInInformation;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User login(Login login) {
    User user = userRepository.findUserByEmailAndPassword(login.getEmail(), login.getPassword())
        .orElseThrow(() -> new InvalidSignInInformation());
    Session session = user.addSession();
    return user;
  }
}
