package com.michaelrichards.followservice.exception;

public class AlreadyFollowingException extends RuntimeException{
    public AlreadyFollowingException(String message) {
        super(message);
    }
}
