package com.chanwoo.chanwoo.repository;

import com.chanwoo.chanwoo.domain.Product;
import com.chanwoo.chanwoo.domain.QProduct;
import com.chanwoo.chanwoo.request.ProductSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.chanwoo.chanwoo.domain.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> getList(ProductSearch productSearch) {
        return jpaQueryFactory.selectFrom(product)
                .limit(productSearch.getSize())
                .offset(productSearch.getOffset())
                .orderBy(product.id.desc())
                .fetch();
    }
}
