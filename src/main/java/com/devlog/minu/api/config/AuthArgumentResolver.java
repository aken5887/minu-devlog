package com.devlog.minu.api.config;

import com.devlog.minu.api.domain.Session;
import com.devlog.minu.api.exception.UnAuthorized;
import com.devlog.minu.api.repository.SessionRepository;
import com.devlog.minu.api.request.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

  private final SessionRepository sessionRepository;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SessionUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    String authorization = webRequest.getHeader("Authorization");
    if(authorization == null || authorization.equals("")){
      throw new UnAuthorized();
    }
    // DB를 이용한 검증
    Session findSession = sessionRepository.findByAccessToken(authorization)
        .orElseThrow(() -> {
          throw new UnAuthorized();
        });

    return findSession.toSessionUser();
  }
}
