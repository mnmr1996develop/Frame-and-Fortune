package com.michaelrichards.followservice.mapper;

import com.michaelrichards.followservice.dto.FollowRelationResponse;
import com.michaelrichards.followservice.entity.Follow;

import java.time.LocalDateTime;

public class FollowMapper {

    private FollowMapper(){}

    public static FollowRelationResponse mapfollowRelationToFollowResponse(
            Follow followRelationship,
            Long followingUserId,
            Long followerUserId,
            Boolean isFollowingBack
    ) {
        return FollowRelationResponse.builder()
                .id(followRelationship.getId())
                .followingId(followerUserId)
                .followerId(followingUserId)
                .isFollowingBack(isFollowingBack)
                .createdDateTime(LocalDateTime.now())
                .build();
    }
}
