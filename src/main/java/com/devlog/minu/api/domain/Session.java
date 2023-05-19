package com.devlog.minu.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String accessToken;

  private LocalDateTime expiredDate;

  @ManyToOne
  private User user;

  @Builder
  public Session(User user){
    this.accessToken = UUID.randomUUID().toString();
    this.expiredDate = user.getCreatedAt().plusMonths(1L);
    this.user = user;
  }
}
