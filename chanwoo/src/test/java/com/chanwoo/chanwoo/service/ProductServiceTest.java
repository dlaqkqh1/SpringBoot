package com.chanwoo.chanwoo.service;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.exception.ProductNotFound;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.request.ProductEdit;
import com.chanwoo.chanwoo.request.ProductSearch;
import com.chanwoo.chanwoo.response.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

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

        ProductResponse response = productService.get(requestProduct.getId());

        assertNotNull(response);
        assertEquals(1L, productRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {

        List<Product> requestProducts = IntStream.range(0, 20)
                        .mapToObj(i -> Product.builder()
                                .title("상품 제목 " + i)
                                .content("상품이에요. " + i)
                                .build())
                .collect(Collectors.toList());

        productRepository.saveAll(requestProducts);

        ProductSearch productSearch = ProductSearch.builder()
                .page(1)
                .size(10)
                .build();

        List<ProductResponse> products = productService.getList(productSearch);

        assertEquals(10L, products.size());

        assertEquals("상품 제목 19", products.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {

        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        ProductEdit productEdit = ProductEdit.builder()
                .title("제목수정")
                .content("내용수정")
                .build();

        productService.edit(product.getId(), productEdit);

        Product changeProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + product.getId()));
        Assertions.assertEquals("제목수정", changeProduct.getTitle());
        Assertions.assertEquals("내용수정", changeProduct.getContent());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test5() {

        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        ProductEdit productEdit = ProductEdit.builder()
                .title(null)
                .content("내용수정")
                .build();

        productService.edit(product.getId(), productEdit);

        Product changeProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + product.getId()));
        Assertions.assertEquals("제목", changeProduct.getTitle());
        Assertions.assertEquals("내용수정", changeProduct.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        productService.delete(product.getId());

        Assertions.assertEquals(0L, productRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test7() {
        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();
        productRepository.save(product);

        assertThrows(ProductNotFound.class, () -> {
            productService.get(product.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        assertThrows(ProductNotFound.class, () -> {
            productService.get(product.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 제목 수정 - 존재하지 않는 글")
    void test9 () {

        Product product = Product.builder()
                .title("제목")
                .content("내용")
                .build();

        productRepository.save(product);

        ProductEdit productEdit = ProductEdit.builder()
                .title("제목수정")
                .content("내용수정")
                .build();


        assertThrows(ProductNotFound.class, () -> {
            productService.edit(product.getId() + 1, productEdit);
        });

    }
}