package com.devlog.minu.product.controller;

import com.devlog.minu.product.dto.ProductServiceFinder;
import com.devlog.minu.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

 private final ProductServiceFinder productServiceFinder;

  @GetMapping("/product/delivery/{type}")
  public String delivery(@PathVariable String type){
    ProductService service = productServiceFinder.find(type);
    return service.delivery();
  }
}
