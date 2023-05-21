package com.devlog.minu.api.controller;

import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.request.PostEdit;
import com.devlog.minu.api.request.PostSearch;
import com.devlog.minu.api.request.SessionUser;
import com.devlog.minu.api.response.PostResponse;
import com.devlog.minu.api.service.PostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @GetMapping("/posts/auth")
  public Long auth(SessionUser sessionUser){
    return sessionUser.getId();
  }

  @GetMapping("/posts/every")
  public String every() {
    return "누구나 접근 가능한 페이지 입니다.";
  }

  @PostMapping("/posts")
  public void post(@RequestBody @Valid PostCreate postCreate){
    postCreate.validate();
    postService.write(postCreate);
  }

  @GetMapping("/posts/{postId}")
  public PostResponse get(@PathVariable Long postId){
    return postService.get(postId);
  }

  @GetMapping("/posts")
  public List<PostResponse> getList(PostSearch postSearch) {
    return postService.getList(postSearch);
  }

  @PatchMapping("/posts/{postId}")
  public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
    return postService.edit(postId, postEdit);
  }

  @DeleteMapping("/posts/{postId}")
  public void delete(@PathVariable Long postId) {
    postService.delete(postId);
  }
}
