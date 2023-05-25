package com.devlog.minu.product.controller;

import com.devlog.minu.product.dto.ProductServiceFinder;
import com.devlog.minu.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

 private final ProductServiceFinder productServiceFinder;

  @GetMapping("/delivery/find/{type}")
  public String find(@PathVariable String type){
    ProductService service = productServiceFinder.find(type);
    return service.delivery();
  }

  @GetMapping("/delivery/get/{type}")
  public String get(@PathVariable String type){
    ProductService service = productServiceFinder.get(type);
    return service.delivery();
  }

  @GetMapping("/delivery/take/{type}")
  public String take(@PathVariable String type) {
    ProductService service = productServiceFinder.take(type);
    return service.delivery();
  }
}
