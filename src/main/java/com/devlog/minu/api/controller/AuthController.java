package com.devlog.minu.api.controller;

import com.devlog.minu.api.config.AppConfig;
import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.request.Login;
import com.devlog.minu.api.request.Signup;
import com.devlog.minu.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.SecretKey;
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
  private final AppConfig appConfig;

  @PostMapping("/auth/login")
  public ResponseEntity<String> login(@RequestBody Login login){
    log.info("login => {}", login);
    User user = userService.login(login);

    SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, +1);
    Date exprDate = cal.getTime();

    String jws = Jwts.builder()
        .setSubject(String.valueOf(user.getId()))
        .signWith(secretKey)
        .setIssuedAt(new Date())
        .setExpiration(exprDate)
        .compact();

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

  @PostMapping("/auth/signup")
  public void signup(@RequestBody Signup signup){
    userService.signup(signup);
  }
}
