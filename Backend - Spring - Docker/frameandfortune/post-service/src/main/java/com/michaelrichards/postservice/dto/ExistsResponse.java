package com.michaelrichards.postservice.dto;


import lombok.Builder;

@Builder
public record ExistsResponse(
        boolean exists
) {
}
