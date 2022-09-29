package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.response.ProductResponse;
import com.chanwoo.chanwoo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public void post(@RequestBody @Valid ProductCreate request) {
        productService.write(request);
    }

    @GetMapping("/products/{productId}")
    public ProductResponse get(@PathVariable(name = "productId") Long id) {
        ProductResponse response = productService.get(id);

        return response;
    }
}
