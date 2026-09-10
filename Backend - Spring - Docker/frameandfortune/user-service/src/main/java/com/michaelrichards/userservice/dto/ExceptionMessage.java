package com.michaelrichards.userservice.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ExceptionMessage(
        HttpStatus status,
        String message,
        LocalDateTime timestamp,
        String path

) {
}
