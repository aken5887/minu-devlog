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

  public void write(PostCreate postCreate){
    Post post = new Post().toEntity(postCreate);
    postRepository.save(post);
  }

  public Post get(Long id){
    return postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
  }
}
