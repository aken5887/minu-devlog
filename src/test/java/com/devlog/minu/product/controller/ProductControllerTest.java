package com.devlog.minu.product.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  @DisplayName("/product/delivery/food, 배송을 조회한다.")
  @Test
  public void test() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/delivery/{type}", "Food"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("배송")));
  }
}