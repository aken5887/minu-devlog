package com.devlog.minu.api.service;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.exception.AlreadyExistEmailException;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Signup;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public void signup(Signup signup) {
    Optional<User> userOptional = userRepository.findUserByEmail(signup.getEmail());
    if(userOptional.isPresent()) {
      throw new AlreadyExistEmailException();
    }
    userRepository.save(signup.toEntity());
  }
}
