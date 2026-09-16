package com.michaelrichards.userservice.dto;


import lombok.Builder;

@Builder
public record ExistsResponse(
        boolean exists
) {
}
