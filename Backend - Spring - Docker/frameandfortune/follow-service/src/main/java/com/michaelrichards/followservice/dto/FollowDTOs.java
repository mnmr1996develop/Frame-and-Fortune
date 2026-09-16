package com.michaelrichards.followservice.dto;

import com.michaelrichards.followservice.entity.Follow;
import lombok.Builder;

import java.time.LocalDateTime;

public class FollowDTOs {

    @Builder
    public record FollowRequest(
            Long followerId,
            Long followingId
    ) {
    }

    public record FollowResponse(
            Long id,
            Long followerId,
            Long followingId,
            LocalDateTime createdAt
    ){
        public static FollowResponse from(Follow follow) {
            return new FollowResponse(
                    follow.getId(),
                    follow.getFollowerId(),
                    follow.getFollowingId(),
                    follow.getCreatedDateTime()
            );
        }
    }


    public record FollowCountsResponse(
            Long userId,
            long followers,
            long following
    ){

    }
}
