package com.michaelrichards.postservice.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String username,
        LocalDate birthDate,
        LocalDateTime lastSeen,
        Boolean isOnline,
        Boolean isUserPrivate
) {
}
