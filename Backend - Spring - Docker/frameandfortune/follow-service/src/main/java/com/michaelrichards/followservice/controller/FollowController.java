package com.michaelrichards.followservice.controller;

import com.michaelrichards.followservice.dto.FollowDTOs;
import com.michaelrichards.followservice.dto.FollowRelationResponse;
import com.michaelrichards.followservice.dto.FollowResponse;
import com.michaelrichards.followservice.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{userId}/followers")
    public ResponseEntity<FollowResponse> getFollowers(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(followService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<FollowResponse> getFollowing(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(followService.getFollowing(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> unfollow(@PathVariable("userId") Long userId, @RequestParam Long followingId) {

        return ResponseEntity.ok().body(followService.unfollowUser(userId, followingId));
    }



    @PostMapping("/{userId}")
    public ResponseEntity<FollowRelationResponse> getFollowings(@PathVariable Long userId, @RequestParam Long followingId) {

        FollowDTOs.FollowRequest  followRequest = FollowDTOs.FollowRequest.builder()
                .followerId(userId)
                .followingId(followingId)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(followService.followUser(followRequest));
    }



}
