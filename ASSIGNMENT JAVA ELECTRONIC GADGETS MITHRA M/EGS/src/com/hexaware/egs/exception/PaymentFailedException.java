package com.hexaware.egs.exception;

public class PaymentFailedException extends Exception {

    public PaymentFailedException() {
        super("Payment processing failed.");
    }

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
