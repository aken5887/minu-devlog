package com.devlog.minu.product.dto;

import com.devlog.minu.product.service.FashionService;
import com.devlog.minu.product.service.FoodService;
import com.devlog.minu.product.service.GeneralService;
import com.devlog.minu.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.WebApplicationContext;

@RequiredArgsConstructor
public enum ProductType {
  Fashion(FashionService.class),
  Food(FoodService.class),
  General(GeneralService.class);

  private final Class<? extends ProductService> productService;

  public ProductService create(WebApplicationContext context) {
    return context.getBean(productService, productService.getClass());
  }
}
