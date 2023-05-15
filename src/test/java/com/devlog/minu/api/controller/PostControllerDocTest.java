package com.devlog.minu.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

  MockMvc mockMvc;

  @Autowired
  PostRepository postRepository;

  @BeforeEach
  void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation))
                        .build();
  }

  @Test
  @DisplayName("글 단건 조회 테스트")
  void test() throws Exception {
      // when
    Post post = Post.builder()
        .title("제목")
        .content("내용")
        .build();
    postRepository.save(post);

    // expected
    this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 1L)
        .accept(APPLICATION_JSON))
        .andDo(print())
		    .andExpect(status().isOk())
        .andDo(document("index"));
  }
}
