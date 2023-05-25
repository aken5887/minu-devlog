package com.devlog.minu.product.dto;

import com.devlog.minu.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductServiceFinder {

  private final List<ProductService> productServices;

  public ProductService find(String type){
    return productServices.stream()
        .filter((productService -> productService.getProductType().name().equals(type)))
        .findAny()
        .orElseThrow(() -> new RuntimeException("해당 타입은 존재하지 않습니다. type="+type));
  }
}
