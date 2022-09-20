package com.chanwoo.chanwoo.service;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.repository.ProductRepository;
import com.chanwoo.chanwoo.request.ProductCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void write(ProductCreate productcreate) {
        Product product = new Product(productcreate.getTitle(), productcreate.getContent());
        productRepository.save(product);
    }
}
