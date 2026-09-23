package com.michaelrichards.followservice.mapper;

import com.michaelrichards.followservice.dto.FollowRelationResponse;
import com.michaelrichards.followservice.entity.FollowInterface;

import java.time.LocalDateTime;

public class FollowMapper {

    private FollowMapper(){}

    public static FollowRelationResponse mapfollowRelationToFollowResponse(
            FollowInterface followRelationship,
            Long followingUserId,
            Long followerUserId,
            boolean nowFollowing,
            Boolean isFollowingBack
    ) {
        return FollowRelationResponse.builder()
                .id(followRelationship.getFollowerId())
                .followingId(followerUserId)
                .followerId(followingUserId)
                .isFollowingBack(isFollowingBack)
                .nowFollowing(nowFollowing)
                .createdDateTime(LocalDateTime.now())
                .build();
    }
}
