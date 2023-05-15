package com.devlog.minu.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.repository.PostRepository;
import com.devlog.minu.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.minu.com", uriPort = 443)
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  PostRepository postRepository;

  @Autowired
  ObjectMapper objectMapper;

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
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
        .accept(APPLICATION_JSON))
        .andDo(print())
		    .andExpect(status().isOk())
        .andDo(document("index",
            RequestDocumentation.pathParameters(
                RequestDocumentation.parameterWithName("postId").description("포스트 ID")
            ),
            PayloadDocumentation.responseFields(
                PayloadDocumentation.fieldWithPath("id").description("아이디"),
                PayloadDocumentation.fieldWithPath("title").description("제목"),
                PayloadDocumentation.fieldWithPath("content").description("내용")
            )
          ));
  }

  @Test
  @DisplayName("글 등록 테스트")
  void test2() throws Exception {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("제목")
        .content("내용")
        .build();
    String body = objectMapper.writeValueAsString(postCreate);

    // expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .content(body))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("posts", PayloadDocumentation.requestFields(
              PayloadDocumentation.fieldWithPath("title").description("제목"),
              PayloadDocumentation.fieldWithPath("content").description("내용")
            )
          )
        );
  }
}
