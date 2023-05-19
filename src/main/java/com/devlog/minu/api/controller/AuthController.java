package com.devlog.minu.api.controller;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

  private final UserService userService;

  @PostMapping("/auth/login")
  public User login(@RequestBody Login login){
    log.info("login => {}", login);
    return userService.login(login);
  }
}
