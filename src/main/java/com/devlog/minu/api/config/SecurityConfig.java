package com.devlog.minu.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
    return http.authorizeHttpRequests()
        .requestMatchers("/auth/login", "/posts/every").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/auth/login")
          .loginProcessingUrl("/auth/login")
          .usernameParameter("username")
          .passwordParameter("password")
          .defaultSuccessUrl("/", true)
          .failureUrl("/login/fail")
        .and()
          .userDetailsService(userDetailsService())
        .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService(){
    InMemoryUserDetailsManager detailsService = new InMemoryUserDetailsManager();
    UserDetails user = User.withUsername("dohyun")
                              .password("12345")
                              .roles("ADMIN")
                              .build();
    detailsService.createUser(user);
    return detailsService;
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
