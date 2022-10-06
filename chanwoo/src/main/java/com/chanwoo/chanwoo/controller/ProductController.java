package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.request.ProductEdit;
import com.chanwoo.chanwoo.request.ProductSearch;
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
    public List<ProductResponse> getList(@ModelAttribute ProductSearch productSearch){
        return productService.getList(productSearch);
    }

    @PatchMapping("/products/{productId}")
    public void edit(@PathVariable Long productId, @RequestBody @Valid ProductEdit request){
        productService.edit(productId, request);
    }

    @DeleteMapping("/products/{productId}")
    public void edit(@PathVariable Long productId){
        productService.delete(productId);
    }
}
