package com.chanwoo.chanwoo.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Setter
@Getter
@Builder
public class ProductSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }

//    @Builder
//    public ProductSearch(Integer page, Integer size) {
//        this.page = page;
//        this.size = size;
//    }
}
