package com.devlog.minu.api.controller;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.response.PostResponse;
import com.devlog.minu.api.service.PostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @PostMapping("/posts")
  public void post(@RequestBody @Valid PostCreate postCreate){
    log.info("postsCreate : {} ", postCreate.toString());
    postService.write(postCreate);
  }

  @GetMapping("/posts/{postId}")
  public PostResponse get(@PathVariable Long postId){
    return postService.get(postId);
  }

  @GetMapping("/posts")
  public List<PostResponse> getList(Pageable pageable) {
    return postService.getList(pageable);
  }
}
