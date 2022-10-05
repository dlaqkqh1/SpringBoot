package com.chanwoo.chanwoo.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductEditor {
    private final String title;
    private final String content;

}
