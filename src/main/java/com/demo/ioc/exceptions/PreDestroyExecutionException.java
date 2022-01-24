package com.demo.ioc.exceptions;

public class PreDestroyExecutionException extends ServiceInstantiationException {
    public PreDestroyExecutionException(String exception) {
        super(exception);
    }

    public PreDestroyExecutionException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
