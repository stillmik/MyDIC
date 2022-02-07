package com.demo.ioc.aa_Part2.Exceptions;

public class ClassRegistrationException extends Exception {

    public ClassRegistrationException(String message) {
        super(message);
    }

    public ClassRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
