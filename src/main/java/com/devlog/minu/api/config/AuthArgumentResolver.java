package com.devlog.minu.api.config;

import com.devlog.minu.api.exception.UnAuthorized;
import com.devlog.minu.api.request.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

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
    return SessionUser.builder().id(1L).build();
  }
}
