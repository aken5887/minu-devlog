package com.devlog.minu.api.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final String errorPage;
  public CustomAccessDeniedHandler(String errorPage){
    this.errorPage = errorPage;
  }
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    request.getRequestDispatcher(errorPage).forward(request, response);
  }
}
