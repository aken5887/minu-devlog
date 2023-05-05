package com.devlog.minu.api.controller;

import com.devlog.minu.api.request.PostCreate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

  @GetMapping("/posts")
  public String get() {
    return "Hello World";
  }

  @PostMapping("/posts")
  public Map<String, String> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult){
    log.info("postsCreate : {} ", postCreate.toString());
    
    if(bindingResult.hasErrors()){
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      String fieldName = fieldErrors.get(0).getField();
      String errorMessage = fieldErrors.get(0).getDefaultMessage();

      Map<String, String> errors = new HashMap<>();
      errors.put(fieldName, errorMessage);
      return errors;
    }

    return Map.of();
  }
}
