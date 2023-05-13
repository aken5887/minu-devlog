package com.devlog.minu.api.service;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.request.PostSearch;
import com.devlog.minu.api.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public void write(PostCreate postCreate){
    Post post = new Post().toEntity(postCreate);
    postRepository.save(post);
  }

  public PostResponse get(Long id){
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));

    PostResponse postResponse = PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .build();

    return postResponse;
  }

  public List<PostResponse> getList(PostSearch postSearch) {
    return postRepository.findAllByPage(postSearch)
        .stream()
        .map(PostResponse::new)
        .collect(Collectors.toList());
  }
}