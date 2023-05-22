package com.devlog.minu.api.service;

import com.devlog.minu.api.crypto.PasswordEncoder;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.exception.AlreadyExistEmailException;
import com.devlog.minu.api.exception.InvalidSignInInformation;
import com.devlog.minu.api.repository.UserRepository;
import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.request.Signup;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public Long login(Login login) {
    User user = userRepository.findUserByEmail(login.getEmail())
        .orElseThrow(() -> new InvalidSignInInformation());
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    boolean matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
    if(!matches){
      throw new InvalidSignInInformation();
    }
    return user.getId();
  }

  public void signup(Signup signup) {
    Optional<User> userOptional = userRepository.findUserByEmail(signup.getEmail());
    if(userOptional.isPresent()) {
      throw new AlreadyExistEmailException();
    }
    userRepository.save(signup.toEntity());
  }
}
