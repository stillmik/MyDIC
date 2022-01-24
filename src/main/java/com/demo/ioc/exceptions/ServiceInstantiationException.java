package com.demo.ioc.exceptions;

public class ServiceInstantiationException extends RuntimeException {

    public ServiceInstantiationException(String exception){
        super(exception);
    }

    public ServiceInstantiationException(String exception, Throwable cause){
        super(exception,cause);
    }
}
