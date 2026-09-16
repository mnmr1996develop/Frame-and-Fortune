package com.michaelrichards.followservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FollowResponse(
        List<UserResponse>users,
        long totalFollowers,
        long totalFollowing) {


}
