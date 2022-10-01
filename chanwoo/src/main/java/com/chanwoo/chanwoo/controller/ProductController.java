package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.response.ProductResponse;
import com.chanwoo.chanwoo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ProductResponse get(@PathVariable(name = "productId") Long productId) {
        ProductResponse response = productService.get(productId);

        return response;
    }

    @GetMapping("/products")
    public List<ProductResponse> getList(Pageable pageable){
        return productService.getList(pageable);
    }
}
