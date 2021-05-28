package com.epam.internship.exception;

public class FlagDisabledException extends RuntimeException {
    public FlagDisabledException(String message) {
        super(message);
    }
}
