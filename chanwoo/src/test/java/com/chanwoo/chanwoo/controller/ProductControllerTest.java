package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.*;
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

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        Product product = Product.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        productRepository.save(product);

        mockMvc.perform(get("/products/{productId}", product.getId())
                        .contentType(APPLICATION_JSON)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {

        Product product1 = productRepository.save(Product.builder()
                .title("title_1")
                .content("content_1")
                .build());

        Product product2 = productRepository.save(Product.builder()
                .title("title_2")
                .content("content_2")
                .build());

        mockMvc.perform(get("/products")
                        .contentType(APPLICATION_JSON)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[0].title").value("title_1"))
                .andExpect(jsonPath("$[0].content").value("content_1"))
                .andExpect(jsonPath("$[1].id").value(product2.getId()))
                .andExpect(jsonPath("$[1].title").value("title_2"))
                .andExpect(jsonPath("$[1].content").value("content_2"))
                .andDo(print());
    }
}