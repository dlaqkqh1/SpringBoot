package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void clean() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("/products 요청 시 title 빈 값을 체크 한다.")
    void test() throws Exception {

        ProductCreate request = ProductCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);

        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/products 요청 시 title 값은 필수다.")
    void test2() throws Exception {

        ProductCreate request = ProductCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // jsonPath
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다.")) // jsonPath
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요.")) // jsonPath
                .andDo(print());
    }

    @Test
    @DisplayName("/products 요청 시 title 값은 필수다.")
    void test3() throws Exception {

        ProductCreate request = ProductCreate.builder()
                .title("제목입니다.")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, productRepository.count());

        Product product = productRepository.findAll().get(0);
        assertEquals("제목입니다.", product.getTitle());
        assertEquals("내용", product.getContent());
    }
}