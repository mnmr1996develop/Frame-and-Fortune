package com.michaelrichards.postservice.exceptions;

public class PageOutOfBoundsException extends RuntimeException {
    public PageOutOfBoundsException(String message) {
        super(message);
    }
}
