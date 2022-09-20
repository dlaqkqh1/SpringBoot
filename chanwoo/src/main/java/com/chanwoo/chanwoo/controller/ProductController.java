package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.request.ProductCreate;
import com.chanwoo.chanwoo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public Map<String, String> post(@RequestBody @Valid ProductCreate request) {
        productService.write(request);
        return Map.of();
    }
}
