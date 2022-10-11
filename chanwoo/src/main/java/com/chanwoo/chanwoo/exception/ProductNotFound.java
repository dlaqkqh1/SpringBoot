package com.chanwoo.chanwoo.exception;


/**
 *   Status --> 404
 */

public class ProductNotFound extends ChanwooException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public ProductNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
