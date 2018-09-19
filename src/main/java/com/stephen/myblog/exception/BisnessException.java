package com.stephen.myblog.exception;

public class BisnessException extends RuntimeException {

    public BisnessException() {
    }

    public BisnessException(String message) {
        super(message);
    }
}
