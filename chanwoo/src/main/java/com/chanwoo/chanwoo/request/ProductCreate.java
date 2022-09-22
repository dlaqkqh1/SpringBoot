package com.chanwoo.chanwoo.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class ProductCreate {
    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public ProductCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
