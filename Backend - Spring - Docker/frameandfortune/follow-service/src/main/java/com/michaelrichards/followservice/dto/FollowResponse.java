package com.michaelrichards.followservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FollowResponse(
        List<UserIdResponse>users,
        long totalFollowers,
        long totalFollowing) {


}
