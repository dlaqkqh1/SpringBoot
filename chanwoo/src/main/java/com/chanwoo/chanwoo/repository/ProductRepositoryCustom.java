package com.chanwoo.chanwoo.repository;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.request.ProductSearch;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getList(ProductSearch productSearch);
}
