package com.devlog.minu.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
