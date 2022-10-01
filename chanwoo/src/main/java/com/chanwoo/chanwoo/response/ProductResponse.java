package com.chanwoo.chanwoo.response;


import com.chanwoo.chanwoo.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
    }

    @Builder
    public ProductResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
