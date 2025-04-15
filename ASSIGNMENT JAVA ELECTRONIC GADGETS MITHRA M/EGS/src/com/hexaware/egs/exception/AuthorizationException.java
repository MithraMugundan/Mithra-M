package com.hexaware.egs.exception;

public class AuthorizationException extends Exception {

    public AuthorizationException() {
        super("You are not authorized to perform this action.");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
