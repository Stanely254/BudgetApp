package com.stanley.budgetwatch.exception;

public class FormatException extends Exception {
    public FormatException(String message) {
        super(message);
    }

    public FormatException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
