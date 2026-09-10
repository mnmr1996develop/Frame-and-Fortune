package com.michaelrichards.userservice.exceptions;

public class TooYoungException extends RuntimeException {
    public TooYoungException(String message) {
        super(message);
    }
}
