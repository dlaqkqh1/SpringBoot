package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.request.ProductEdit;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                        .contentType(APPLICATION_JSON)                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지 0으로 조회해도 1페이지 조회")
    void test5() throws Exception {

        List<Product> requestProducts = IntStream.range(0, 20)
                .mapToObj(i -> Product.builder()
                        .title("상품 제목 " + i)
                        .content("상품이에요. " + i)
                        .build())
                .collect(Collectors.toList());

        productRepository.saveAll(requestProducts);

        mockMvc.perform(get("/products?page=0&size=10")
                        .contentType(APPLICATION_JSON)) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("상품 제목 19"))
                .andExpect(jsonPath("$[0].content").value("상품이에요. 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test6() throws Exception {

        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        ProductEdit productEdit = ProductEdit.builder()
                .title("제목수정")
                .content("내용수정")
                .build();

        mockMvc.perform(patch("/products/{productId}", product.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productEdit))) // application/json
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception {

        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        mockMvc.perform(delete("/products/{productId}", product.getId())
                        .contentType(APPLICATION_JSON)) // application/json
                .andExpect(status().isOk())
                .andDo(print());
    }
}