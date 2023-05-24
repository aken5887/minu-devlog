package com.devlog.minu.api.config;

import com.devlog.minu.api.domain.User;
import com.devlog.minu.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
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
        .requestMatchers("/guest").hasRole("GUEST")
        .requestMatchers("/user").hasAnyRole("USER","ADMIN")
        .requestMatchers("/admin")
          .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
        .anyRequest().permitAll()
        .and()
          .formLogin()
            .loginPage("/auth/login")
            .loginProcessingUrl("/auth/login")
            .defaultSuccessUrl("/")
            .failureForwardUrl("/login/fail")
            .usernameParameter("username")
            .passwordParameter("password")
        .and()
          .rememberMe(rm -> rm.rememberMeParameter("remember")
            .alwaysRemember(false)
            .tokenValiditySeconds(2592000))
          .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        .exceptionHandling()
          .accessDeniedHandler(accessDeniedHandler())
        .and()
        .build();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler(){
    return new CustomAccessDeniedHandler("/forbidden");
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
