package com.michaelrichards.userservice.dto;

import java.time.LocalDate;


public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        LocalDate birthDate
) {
}
