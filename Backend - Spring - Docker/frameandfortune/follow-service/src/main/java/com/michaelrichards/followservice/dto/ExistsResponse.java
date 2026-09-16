package com.michaelrichards.followservice.dto;


import lombok.Builder;

@Builder
public record ExistsResponse(
        boolean exists
) {
}
