package com.devlog.minu.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.devlog.minu.api.request.PostEdit;
import com.devlog.minu.api.request.PostSearch;
import com.devlog.minu.api.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

@SpringBootTest
class PostServiceTest {

  @Autowired
  PostService postService;

  @Autowired
  PostRepository postRepository;

  @BeforeEach
  void clean(){
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("Post 엔티티를 저장한다.")
  void write(){
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("foo")
        .content("bar")
        .build();

    // when
    postService.write(postCreate);

    // then
    assertThat(postRepository.count()).isEqualTo(1L);
  }

  @Test
  @DisplayName("저장된 Post 엔티티를 조회한다.")
  void get(){
    // given
    Post savedPost = Post.builder()
        .title("123456789012345")
        .content("content")
        .build();

    postRepository.save(savedPost);

    // when
    PostResponse response = postService.get(savedPost.getId());

    // then
    assertThat(response).isNotNull();
    assertThat(response.getTitle()).isEqualTo("1234567890");
    assertThat(response.getContent()).isEqualTo(savedPost.getContent());
  }

  @Test
  @DisplayName("없는 게시글을 조회할 때 익셉션을 발생시킨다.")
  void get_exception(){
    // given
    // when
    // then
    assertThatThrownBy(
        () -> postService.get(1L)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("여러 건의 데이터 중 1페이지를 조회한다.")
  void get_list(){
    // given
    List<Post> postList = IntStream.range(1, 31)
            .mapToObj(
                i -> Post.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .build()
            ).collect(Collectors.toList());

    postRepository.saveAll(postList);

    PostSearch postSearch = PostSearch.builder()
        .page(1)
        .build();
    // when
    List<PostResponse> posts = postService.getList(postSearch);
    // then
    assertThat(posts.size()).isEqualTo(10);
    assertThat(posts.get(0).getTitle()).isEqualTo("제목 30");
    assertThat(posts.get(0).getContent()).isEqualTo("내용 30");
  }

  @DisplayName("글 제목/내용 수정 테스트")
  @Test
  void edit() {
    // given
    Post post = new Post("제목", "내용");
    postRepository.save(post);

    PostEdit postEdit = PostEdit.builder()
        .title("김수영")
        .content("퍼레이드")
        .build();

    // when
    postService.edit(post.getId(), postEdit);

    //then
    System.out.println(post.toString());
    Post savedPost = postRepository.findById(post.getId())
        .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다. id="+post.getId()));
    assertThat(savedPost.getTitle()).isEqualTo("김수영");
    assertThat(savedPost.getContent()).isEqualTo("퍼레이드");
  }
}