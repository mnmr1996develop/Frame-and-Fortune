package com.michaelrichards.followservice.service;

import com.michaelrichards.followservice.client.UserClient;
import com.michaelrichards.followservice.dto.FollowDTOs;
import com.michaelrichards.followservice.dto.FollowRelationResponse;
import com.michaelrichards.followservice.dto.FollowResponse;
import com.michaelrichards.followservice.dto.UserResponse;
import com.michaelrichards.followservice.entity.Follow;
import com.michaelrichards.followservice.exception.InvalidFollowException;
import com.michaelrichards.followservice.exception.UserNotFoundException;
import com.michaelrichards.followservice.mapper.FollowMapper;
import com.michaelrichards.followservice.repository.FollowRelationRepository;
import com.michaelrichards.followservice.repository.FollowRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserClient userClient;

    private final FollowRelationRepository followRelationRepository;

    private final FollowRequestRepository followRequestRepository;

    public FollowResponse getFollowers(Long userId) {

        userClient.updateLastSeen(userId);

        List<UserResponse> followers = followRelationRepository.findByFollowingId(userId)
                .stream()
                .map(follow -> UserResponse.builder().userId(follow.getFollowerId()).build())
                .toList();

        return FollowResponse.builder()
                .users(followers)
                .totalFollowers(followRelationRepository.countByFollowerId(userId))
                .totalFollowing(followRelationRepository.countByFollowingId(userId))
                .build();
    }


    public FollowResponse getFollowing(Long userId) {
        userClient.updateLastSeen(userId);
        List<UserResponse> following = followRelationRepository.findByFollowerId(userId)
                .stream()
                .map(follow -> UserResponse.builder().userId(follow.getFollowingId()).build())
                .toList();


        return FollowResponse.builder()
                .users(following)
                .totalFollowers(followRelationRepository.countByFollowingId(userId))
                .totalFollowing(followRelationRepository.countByFollowerId(userId))
                .build();

    }

    @Transactional
    public FollowRelationResponse followUser(FollowDTOs.FollowRequest followRequest) {

        Long followerId = followRequest.followerId();
        Long followingId = followRequest.followingId();

        if (followerId == null || followingId == null) {
            throw new InvalidFollowException("A user cannot be null");
        }
        if (followerId.equals(followingId)) {
            String message = "Follower and following IDs are the same";
            log.error(message);
            throw new InvalidFollowException(message);
        }
        if (!userClient.userExists(followerId)) {
            throw new UserNotFoundException("User with id:" + followerId + "does not exist");
        }
        if (!userClient.userExists(followingId)) {
            throw new UserNotFoundException("User with id:" + followingId + "does not exist");
        }
        if (followRelationRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new InvalidFollowException("Follower: " + followerId + " is already following: " + followingId);
        }


        Follow follow = Follow.builder()
                .createdDateTime(LocalDateTime.now())
                .followingId(followingId)
                .followerId(followerId)
                .build();

        Follow savedFollow = followRelationRepository.save(follow);

        return FollowMapper
                .mapfollowRelationToFollowResponse(
                        savedFollow,
                        followingId,
                        followerId,
                        followRelationRepository.existsByFollowerIdAndFollowingId(followingId, followerId)
                );

    }

    @Transactional
    public Boolean unfollowUser(Long followerId, Long followingId) {
        if (followerId == null || followingId == null) {
            throw new InvalidFollowException("A user cannot be null");
        }
        if (followerId.equals(followingId)) {
            throw new InvalidFollowException("Follower and following IDs are the same");
        }

        if (!followRelationRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new InvalidFollowException("Follower: " + followerId + " and following: " + followingId + " does not exist");
        }

        log.info("Attempting to unfollow user: {} and following: {}", followerId, followingId);
        long deletedRelationShips = followRelationRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);

        if (deletedRelationShips == 1)
            return true;
        else {
            log.info("{} followerId {} and followingId {} are not deleted", deletedRelationShips, followerId, followingId);
            throw new RuntimeException("Something went wrong");
        }

    }


}
