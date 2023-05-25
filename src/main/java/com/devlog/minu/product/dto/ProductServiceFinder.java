package com.devlog.minu.product.dto;

import com.devlog.minu.product.service.ProductService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceFinder {

  private final List<ProductService> productServices;
  private final Map<String, ProductService> productServiceMap;
  private final WebApplicationContext applicationContext;

  public ProductService find(String type){
    return productServices.stream()
        .filter((productService -> productService.getProductType().name().equals(type)))
        .findAny()
        .orElseThrow(() -> new RuntimeException("해당 타입은 존재하지 않습니다. type="+type));
  }

  public ProductService get(String type){
    log.info("Map : {}, Key : {}", productServiceMap.values(), productServiceMap.keySet());
    return productServiceMap.get(type.toLowerCase()+"Service");
  }

  public ProductService take(String type){
    return ProductType.valueOf(type).create(applicationContext);
  }
}
