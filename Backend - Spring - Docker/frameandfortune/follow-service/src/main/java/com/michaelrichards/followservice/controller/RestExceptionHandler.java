package com.michaelrichards.followservice.controller;

import com.michaelrichards.followservice.dto.ExceptionMessage;
import com.michaelrichards.followservice.exception.AlreadyFollowingException;
import com.michaelrichards.followservice.exception.FollowNotFoundException;
import com.michaelrichards.followservice.exception.InvalidFollowException;
import com.michaelrichards.followservice.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
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


    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<ExceptionMessage> handleAlreadyFollowingException(AlreadyFollowingException ex, WebRequest request) {
        log.error(ex.getMessage());

        HttpStatus status = HttpStatus.CONFLICT;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(status)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(status)
                .body(exceptionMessage);
    }


    @ExceptionHandler(InvalidFollowException.class)
    public ResponseEntity<ExceptionMessage> handleInvalidFollowException(InvalidFollowException ex, WebRequest request) {
        log.error(ex.getMessage());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(httpStatus)
                .body(exceptionMessage);

    }

    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleFollowNotFoundException(FollowNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        HttpStatusCode httpStatus = HttpStatus.NOT_FOUND;
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(httpStatus)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        HttpStatusCode httpStatus = HttpStatus.NOT_FOUND;
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(httpStatus)
                .body(exceptionMessage);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ExceptionMessage> handle(HttpStatusCodeException ex, WebRequest request) {
        log.error(ex.getMessage());
        HttpStatusCode status = ex.getStatusCode();
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(status)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return ResponseEntity.status(status)
                .body(exceptionMessage);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(httpStatus)
                .body(exceptionMessage);
    }*/


}
