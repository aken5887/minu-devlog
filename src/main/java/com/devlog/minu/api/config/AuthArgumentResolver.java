package com.devlog.minu.api.config;

import com.devlog.minu.api.exception.UnAuthorized;
import com.devlog.minu.api.repository.SessionRepository;
import com.devlog.minu.api.request.SessionUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

  private final SessionRepository sessionRepository;
  private final AppConfig appConfig;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SessionUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    if(servletRequest == null){
      log.error("servletRequest is null");
      throw new UnAuthorized();
    }

    Cookie[] cookies = servletRequest.getCookies();
    if(cookies == null || cookies.length == 0){
      log.error("퀴가 없음");
      throw new UnAuthorized();
    }

    String jws = cookies[0].getValue();

    try {
      Jws<Claims> claims
          = Jwts.parserBuilder()
          .setSigningKey(appConfig.getJwtKey())
          .build()
          .parseClaimsJws(jws);

      String userId = claims.getBody().getSubject();
      Date exprDate = claims.getBody().getExpiration();

      if(exprDate == null || exprDate.before(new Date())){
        log.error("올바르지 않은 Token 값 입니다.");
        throw new UnAuthorized();
      }

      return SessionUser.builder()
          .id(Long.parseLong(userId))
          .build();
    } catch(JwtException e){
      log.error("JWT Token이 올바르지 않습니다.");
    }

    return null;
  }
}

