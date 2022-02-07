package com.demo.ioc.aa_Part2.Exceptions;

public class InterfaceNotFoundException extends Exception {

    public InterfaceNotFoundException(String message) {
        super(message);
    }

    public InterfaceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
