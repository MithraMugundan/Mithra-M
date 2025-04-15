package com.hexaware.egs.exception;

public class SqlException extends Exception {

    public SqlException() {
        super("Database operation failed.");
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
