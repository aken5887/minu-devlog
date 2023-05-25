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

  @DisplayName("/product/delivery/find/food, 배송을 조회한다.")
  @Test
  public void test() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/delivery/find/{type}", "Food"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("배송")));
  }

  @DisplayName("/product/delivery/get/food, 배송을 조회한다.")
  @Test
  public void test2() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/delivery/get/{type}", "Food"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("배송")));
  }

  @DisplayName("/product/delivery/take/food, 배송을 조회한다.")
  @Test
  public void test3() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/delivery/take/{type}", "Food"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("배송")));
  }
}