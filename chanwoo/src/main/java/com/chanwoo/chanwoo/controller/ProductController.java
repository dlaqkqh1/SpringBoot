package com.chanwoo.chanwoo.controller;

import com.chanwoo.chanwoo.request.ProductCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class ProductController {

    @PostMapping("/products")
    public String post(@RequestBody ProductCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }
}
