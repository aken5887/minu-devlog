package com.devlog.minu.api.service;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.domain.PostEditor;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.request.PostEdit;
import com.devlog.minu.api.request.PostSearch;
import com.devlog.minu.api.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public PostResponse edit(Long postId, PostEdit postEdit){
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

    PostEditor.PostEditorBuilder postEditorBuilder
        = post.toEditor();

    PostEditor postEditor = postEditorBuilder
        .title(postEdit.getTitle())
        .content(postEdit.getContent())
        .build();

    post.edit(postEditor);

    return new PostResponse(post);
  }

  public void delete(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    postRepository.delete(post);
  }
}