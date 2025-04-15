package com.hexaware.egs.exception;

public class IncompleteOrderException extends Exception {

    public IncompleteOrderException() {
        super("Order is incomplete or missing required details.");
    }

    public IncompleteOrderException(String message) {
        super(message);
    }

    public IncompleteOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
