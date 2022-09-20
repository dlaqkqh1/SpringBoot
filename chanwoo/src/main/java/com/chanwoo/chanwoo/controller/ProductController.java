package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.request.ProductCreate;
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
public class ProductController {

    @PostMapping("/products")
    public Map<String, String> post(@RequestBody @Valid ProductCreate params) {
            return Map.of();
    }
}
