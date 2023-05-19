package com.devlog.minu.api.repository;

import com.devlog.minu.api.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByEmailAndPassword(String email, String password);
}
