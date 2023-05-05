package com.devlog.minu.api.service;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.domain.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public void save(PostCreate postCreate){
    Post post = new Post().toEntity(postCreate);
    postRepository.save(post);
  }
}
