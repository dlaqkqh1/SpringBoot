package com.chanwoo.chanwoo.service;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void write(ProductCreate productcreate) {
        Product product = Product.builder()
                .title(productcreate.getTitle())
                .content(productcreate.getContent())
                .build();

        productRepository.save(product);
    }

    public ProductResponse get(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .content(product.getContent())
                .build();
    }

    // JPA 상속 받으면서 findAll(Pageable) 이 사용 가능
    public List<ProductResponse> getList(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
