package com.michaelrichards.followservice.controller;

import com.michaelrichards.followservice.dto.FollowRelationResponse;
import com.michaelrichards.followservice.dto.FollowRequestDTO;
import com.michaelrichards.followservice.dto.FollowResponse;
import com.michaelrichards.followservice.dto.UserIdResponse;
import com.michaelrichards.followservice.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}/followRequest")
    public ResponseEntity<List<UserIdResponse>> getFollowRequest(@PathVariable("userId") Long userId, @RequestParam("pageNumber") int pageNumber) {
        return ResponseEntity.ok(followService.getFollowRequests(userId, pageNumber));
    }

    @GetMapping("{userId}/sentRequest")
    public ResponseEntity<List<UserIdResponse>> getSentFollowRequest(@PathVariable("userId") Long userId,  @RequestParam("pageNumber") int pageNumber) {
        return ResponseEntity.ok(followService.getSentFollowRequests(userId, pageNumber));
    }

    @GetMapping("{userId}/accept")
    public ResponseEntity<FollowRelationResponse> getSentFollowRequest(@PathVariable("userId") Long userId, @RequestParam Long followerId) {
        HttpStatus status = HttpStatus.CREATED;
        return ResponseEntity.status(status).body(followService.acceptFollowRequest(userId, followerId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> unfollow(@PathVariable("userId") Long userId, @RequestParam Long followingId) {

        return ResponseEntity.ok().body(followService.unfollowUser(userId, followingId));
    }



    @PostMapping("/{userId}")
    public ResponseEntity<FollowRelationResponse> follow(@PathVariable Long userId, @RequestParam Long followingId) {

        FollowRequestDTO  followRequest = FollowRequestDTO.builder()
                .userId(userId)
                .followingId(followingId)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(followService.followUser(followRequest));
    }



}
