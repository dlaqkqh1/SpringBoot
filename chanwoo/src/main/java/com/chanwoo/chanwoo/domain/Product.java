package com.chanwoo.chanwoo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Product(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ProductEditor.ProductEditorBuilder toEditer() {
        return ProductEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(ProductEditor productEditor) {
        this.title = productEditor.getTitle();
        this.content = productEditor.getContent();
    }

}
