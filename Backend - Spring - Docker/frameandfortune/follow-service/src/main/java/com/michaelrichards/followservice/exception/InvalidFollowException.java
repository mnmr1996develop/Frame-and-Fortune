package com.michaelrichards.followservice.exception;

public class InvalidFollowException extends RuntimeException {
    public InvalidFollowException(String message) {
        super(message);
    }
}
