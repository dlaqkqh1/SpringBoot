package com.chanwoo.chanwoo.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ChanwooException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();
    public ChanwooException(String message) {
        super(message);
    }

    public ChanwooException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
