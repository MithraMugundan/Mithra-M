package com.hexaware.egs.exception;

public class InsufficientStockException extends Exception {

    public InsufficientStockException() {
        super("Insufficient stock available.");
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
