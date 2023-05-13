package com.devlog.minu.api.repository;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.domain.QPost;
import com.devlog.minu.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Post> findAllByPage(PostSearch postSearch) {
    return this.queryFactory.selectFrom(QPost.post)
        .limit(postSearch.getSize())
        .offset(postSearch.getOffSet())
        .orderBy(QPost.post.id.desc())
        .fetch();
  }
}
