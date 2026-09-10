package com.michaelrichards.userservice.controller;

import com.michaelrichards.userservice.dto.ExceptionMessage;
import com.michaelrichards.userservice.exceptions.EmailAlreadyTakenException;
import com.michaelrichards.userservice.exceptions.UsernameAlreadyTakenException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error(ex.getMessage());

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exceptionMessage);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ExceptionMessage> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex, WebRequest request) {
        log.error(ex.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(status).message(ex.getMessage()).timestamp(LocalDateTime.now()).path(request.getDescription(false)).build();

        return  ResponseEntity.status(status)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ExceptionMessage> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex, WebRequest request) {
        log.error(ex.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(status)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return  ResponseEntity.status(status)
                .body(exceptionMessage);
    }

}
