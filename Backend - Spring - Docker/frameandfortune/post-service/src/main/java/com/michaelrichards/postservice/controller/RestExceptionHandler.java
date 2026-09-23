package com.michaelrichards.postservice.controller;

import com.michaelrichards.postservice.exceptions.CommentNotFoundException;
import com.michaelrichards.postservice.exceptions.PageOutOfBoundsException;
import com.michaelrichards.postservice.exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {




    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleException(CommentNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handleException(PostNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(PageOutOfBoundsException.class)
    public ResponseEntity<String> handleException(PageOutOfBoundsException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleException(IllegalStateException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(ex.getMessage());
    }


}
