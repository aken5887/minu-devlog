package com.devlog.minu.api.repository;

import com.devlog.minu.api.domain.Post;
import com.devlog.minu.api.request.PostSearch;
import java.util.List;

public interface PostRepositoryCustom {
  List<Post> findAllByPage(PostSearch postSearch);
}
