package com.chanwoo.chanwoo.service;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void clean() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        ProductCreate productcreate = ProductCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        productService.write(productcreate);

        assertEquals(1L, productRepository.count());
        Product product = productRepository.findAll().get(0);
        assertEquals("제목입니다.", product.getTitle());
        assertEquals("내용입니다.", product.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        Product requestProduct = Product.builder()
                .title("foo")
                .content("bar")
                .build();
        productRepository.save(requestProduct);

        Long productId = 2L;
        ProductResponse response = productService.get(productId);

        assertNotNull(response);
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

}