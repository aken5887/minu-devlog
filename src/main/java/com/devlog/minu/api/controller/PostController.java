package com.devlog.minu.api.controller;

import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.service.PostService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @GetMapping("/posts")
  public String get() {
    return "Hello World";
  }

  @PostMapping("/posts")
  public void post(@RequestBody @Valid PostCreate postCreate){
    log.info("postsCreate : {} ", postCreate.toString());
    postService.save(postCreate);
  }
}
