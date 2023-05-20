package com.devlog.minu.api.repository;

import com.devlog.minu.api.domain.Session;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
  Optional<Session> findByAccessToken(String accessToken);
}
