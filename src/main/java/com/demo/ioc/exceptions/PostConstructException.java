package com.demo.ioc.exceptions;

public class PostConstructException extends ServiceInstantiationException {
    public PostConstructException(String exception) {
        super(exception);
    }

    public PostConstructException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
