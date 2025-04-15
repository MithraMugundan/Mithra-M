package com.hexaware.egs.exception;

public class ConcurrencyException extends Exception {

    public ConcurrencyException() {
        super("Concurrent modification detected.");
    }

    public ConcurrencyException(String message) {
        super(message);
    }

    public ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
