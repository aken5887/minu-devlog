package com.devlog.minu.api.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="TB_USER")
public class User extends BaseTimeEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Session> sessions = new ArrayList<>();

  @Builder
  public User(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public Session addSession(){
    Session newSession = Session.builder().user(this).build();
    sessions.add(newSession);
    return newSession;
  }
}
