package com.michaelrichards.followservice.dto;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
public record UserResponse (
    Long userId
){
}

