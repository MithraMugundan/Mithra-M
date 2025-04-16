package com.hexaware.ims.exceptions;

public class PolicyNotFoundException extends Exception {

    public PolicyNotFoundException() {
        super("Policy not found.");
    }

    public PolicyNotFoundException(String message) {
        super(message);
    }
}
