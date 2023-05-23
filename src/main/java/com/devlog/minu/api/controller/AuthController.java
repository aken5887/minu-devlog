package com.devlog.minu.api.controller;

import com.devlog.minu.api.config.AppConfig;
import com.devlog.minu.api.request.Signup;
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
  private final AppConfig appConfig;

  @PostMapping("/auth/signup")
  public void signup(@RequestBody Signup signup){
    userService.signup(signup);
  }
}
