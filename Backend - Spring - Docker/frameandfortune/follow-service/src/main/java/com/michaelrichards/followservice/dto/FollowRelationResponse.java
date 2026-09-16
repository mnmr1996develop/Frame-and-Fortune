package com.michaelrichards.followservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FollowRelationResponse(
        Long id,
        Long followerId,
        Long followingId,
        boolean isFollowingBack,
        LocalDateTime createdDateTime
) {
}
