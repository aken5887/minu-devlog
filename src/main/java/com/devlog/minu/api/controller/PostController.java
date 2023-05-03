package com.devlog.minu.api.controller;

import com.devlog.minu.api.request.PostCreate;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

  @GetMapping("/posts")
  public String get() {
    return "Hello World";
  }

  @PostMapping("/posts")
  public String post(@RequestBody PostCreate postCreate){
    log.info("postsCreate : {} ", postCreate.toString());
    return "Hello World";
  }
}
