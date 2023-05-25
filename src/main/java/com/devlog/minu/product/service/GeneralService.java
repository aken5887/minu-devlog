package com.devlog.minu.product.service;

import com.devlog.minu.product.dto.ProductType;
import org.springframework.stereotype.Service;

@Service
public class GeneralService implements ProductService{
  @Override
  public String delivery() {
    return "위탁 배송 대상입니다.";
  }

  @Override
  public ProductType getProductType() {
    return ProductType.General;
  }
}
