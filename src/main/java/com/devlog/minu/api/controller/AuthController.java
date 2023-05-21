package com.devlog.minu.api.controller;

import com.devlog.minu.api.config.JwtKey;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.service.UserService;
import io.jsonwebtoken.Jwts;
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
    User user = userService.login(login);

    String jws = Jwts.builder()
        .setSubject(String.valueOf(user.getId()))
        .signWith(JwtKey.getKey())
        .compact();

    log.info(">>>>>>>>>>> jwtKet : {}", JwtKey.getStrKey());

    ResponseCookie responseCookie = ResponseCookie.from("SESSION", jws)
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
