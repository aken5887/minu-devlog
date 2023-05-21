package com.devlog.minu.api.controller;

import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.response.SessionResponse;
import com.devlog.minu.api.service.UserService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

  private final UserService userService;

  @PostMapping("/auth/login")
  public ResponseEntity<String> login(@RequestBody Login login){
    log.info("login => {}", login);
    SessionResponse sessionResponse = userService.login(login);
    ResponseCookie responseCookie = ResponseCookie.from("SESSION", sessionResponse.getAccessToken())
        .domain("localhost")
        .path("/")
        .httpOnly(true)
        .secure(false)
        .maxAge(Duration.ofDays(30))
        .sameSite("Strict")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .build();
  }
}
