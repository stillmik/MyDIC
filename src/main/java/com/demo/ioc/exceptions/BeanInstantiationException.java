package com.demo.ioc.exceptions;

public class BeanInstantiationException extends ServiceInstantiationException {
    public BeanInstantiationException(String exception) {
        super(exception);
    }

    public BeanInstantiationException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
