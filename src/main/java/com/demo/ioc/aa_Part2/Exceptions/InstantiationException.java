package com.demo.ioc.aa_Part2.Exceptions;

public class InstantiationException extends Exception {

    public InstantiationException(String message) {
        super(message);
    }

    public InstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
