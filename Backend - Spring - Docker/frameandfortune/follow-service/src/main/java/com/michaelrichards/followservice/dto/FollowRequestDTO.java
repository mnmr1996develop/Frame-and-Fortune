package com.michaelrichards.followservice.dto;

import lombok.Builder;

@Builder
public record FollowRequestDTO(
        Long userId,
        Long followingId
) {
}
