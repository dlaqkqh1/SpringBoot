package com.chanwoo.chanwoo.exception;

public class ProductNotFound extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public ProductNotFound() {
        super(MESSAGE);
    }
}
