package com.devlog.minu.api.config;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(){
    return new WebSecurityCustomizer() {
      @Override
      public void customize(WebSecurity web) {
        web.ignoring()
            .requestMatchers("/favicon.ico")
            .requestMatchers("/error")
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
            .requestMatchers(new AntPathRequestMatcher("/docs/**"));
      }
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests()
        .requestMatchers("/posts/auth").authenticated()
        .anyRequest().permitAll()
        .and()
          .formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginPage("/auth/login")
            .loginProcessingUrl("/auth/login")
            .defaultSuccessUrl("/")
            .failureForwardUrl("/login/fail")
        .and()
          .rememberMe(rm -> rm.rememberMeParameter("remember")
            .alwaysRemember(false)
            .tokenValiditySeconds(2592000))
          .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository){
    return username -> {
      User user = userRepository.findUserByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException(username+"을 찾을 수 없습니다."));
      return new UserPrincipal(user);
    };
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return com.devlog.minu.api.crypto.PasswordEncoder.getEncoder();
  }
}
