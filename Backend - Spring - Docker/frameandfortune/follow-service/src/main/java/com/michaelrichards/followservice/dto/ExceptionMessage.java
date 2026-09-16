package com.michaelrichards.followservice.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Builder
public record ExceptionMessage(
        HttpStatusCode status,
        String message,
        LocalDateTime timestamp,
        String path

) {
}
