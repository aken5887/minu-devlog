package com.devlog.minu.api.controller;

import com.devlog.minu.api.exception.CommonException;
import com.devlog.minu.api.exception.InvalidRequest;
import com.devlog.minu.api.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse invalidRequestErrorHandler(MethodArgumentNotValidException e){
    log.error("Exception : {}", e);
    ErrorResponse errorResponse = ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message("잘못된 요청입니다.")
        .validation(new HashMap<>())
        .build();
    for(FieldError fieldError: e.getFieldErrors()){
      errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return errorResponse;
  }

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorResponse> commonException(CommonException e){
    int statusCode = e.getStatusCode();
    Map<String, Object> validation = new HashMap<>();

    if(e instanceof InvalidRequest){
      InvalidRequest invalidRequest = (InvalidRequest) e;
      validation.put(invalidRequest.getField(), invalidRequest.getFieldMessage());
    }

    ErrorResponse errorResponse = ErrorResponse.builder()
        .code(statusCode)
        .message(e.getMessage())
        .validation(validation)
        .build();

    ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode).body(errorResponse);

    return response;
  }
}
