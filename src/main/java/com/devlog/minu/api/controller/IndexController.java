package com.devlog.minu.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {
  @GetMapping("/")
  public String index() {
    return "메인페이지 입니다.";
  }

  @GetMapping("/login/fail")
  public String fail() {
    return "로그인에 실패하였습니다.";
  }

  @GetMapping("/user")
  public String user(){
    return "사용자 전용 페이지 입니다. welcome 🎉";
  }

  @GetMapping("/admin")
  public String admin() {
    return "관리자 전용 페이지 입니다. go back 🎃";
  }

  @GetMapping("/forbidden")
  public String forbidden() {
    return "해당 페이지에 접근 권한이 없습니다. ⛏";
  }

  @GetMapping("/guest")
  public String guest(){
    return "게스트 전용 페이지 입니다. guest page 🏑";
  }
}
