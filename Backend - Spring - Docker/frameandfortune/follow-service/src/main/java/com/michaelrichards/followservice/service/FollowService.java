package com.michaelrichards.followservice.service;

import com.michaelrichards.followservice.client.UserClient;
import com.michaelrichards.followservice.dto.*;
import com.michaelrichards.followservice.entity.FollowRelation;
import com.michaelrichards.followservice.entity.FollowRequests;
import com.michaelrichards.followservice.exception.InvalidFollowException;
import com.michaelrichards.followservice.exception.UserNotFoundException;
import com.michaelrichards.followservice.mapper.FollowMapper;
import com.michaelrichards.followservice.repository.FollowRelationRepository;
import com.michaelrichards.followservice.repository.FollowRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        List<UserIdResponse> followers = followRelationRepository.findByFollowingId(userId)
                .stream()
                .map(followRelation -> UserIdResponse.builder().userId(followRelation.getFollowerId()).build())
                .toList();

        return FollowResponse.builder()
                .users(followers)
                .totalFollowers(followRelationRepository.countByFollowingId(userId))
                .totalFollowing(followRelationRepository.countByFollowerId(userId))
                .build();
    }


    public FollowResponse getFollowing(Long userId) {
        userClient.updateLastSeen(userId);
        List<UserIdResponse> following = followRelationRepository.findByFollowerId(userId)
                .stream()
                .map(followRelation -> UserIdResponse.builder().userId(followRelation.getFollowingId()).build())
                .toList();


        return FollowResponse.builder()
                .users(following)
                .totalFollowers(followRelationRepository.countByFollowingId(userId))
                .totalFollowing(followRelationRepository.countByFollowerId(userId))
                .build();

    }

    @Transactional
    public FollowRelationResponse followUser(FollowRequestDTO followRequest) {

        Long userId = followRequest.userId();
        Long followingId = followRequest.followingId();

        userClient.updateLastSeen(userId);

        if (userId == null || followingId == null) {
            throw new InvalidFollowException("A user cannot be null");
        }
        if (userId.equals(followingId)) {
            String message = "Follower and following IDs are the same";
            log.error(message);
            throw new InvalidFollowException(message);
        }
        if (!userClient.userExists(userId)) {
            throw new UserNotFoundException("User with id:" + userId + "does not exist");
        }
        if (!userClient.userExists(followingId)) {
            throw new UserNotFoundException("User with id:" + followingId + "does not exist");
        }
        if (followRelationRepository.existsByFollowerIdAndFollowingId(userId, followingId)) {
            throw new InvalidFollowException("Follower: " + userId + " is already following: " + followingId);
        }



        UserResponse user = userClient.getUserById(followingId);
        if (user == null) {
            throw new UserNotFoundException("User with id:" + followingId + "does not exist");
        }

        if (!user.isUserPrivate()){

            FollowRelation followRelation = new FollowRelation();
            followRelation.setCreatedTimestamp(LocalDateTime.now());
            followRelation.setFollowingId(followingId);
            followRelation.setFollowerId(userId);

            FollowRelation savedFollowRelation = followRelationRepository.save(followRelation);

            return FollowMapper
                    .mapfollowRelationToFollowResponse(
                            savedFollowRelation,
                            followingId,
                            userId,
                            true,
                            followRelationRepository.existsByFollowerIdAndFollowingId(followingId, userId)
                    );
        }

        if (followRelationRepository.existsByFollowerIdAndFollowingId(userId, followingId)) {
            throw new InvalidFollowException("Follower: " + userId + " already sent a follower request to: " + followingId);
        }


        FollowRequests followRequests = new FollowRequests();
        followRequests.setCreatedTimestamp(LocalDateTime.now());
        followRequests.setFollowingId(followingId);
        followRequests.setFollowerId(userId);
        FollowRequests savedFollowRequests = followRequestRepository.save(followRequests);

        return FollowMapper.mapfollowRelationToFollowResponse(
                savedFollowRequests,
                followingId,
                userId,
                false,
                followRelationRepository.existsByFollowerIdAndFollowingId(followingId, userId)
        );

    }

    public List<UserIdResponse> getFollowRequests(Long userId, int pageNumber) {
        userClient.updateLastSeen(userId);

        Pageable pageable = PageRequest.of(pageNumber-1, 25,  Sort.by(Sort.Direction.DESC, "createdTimestamp"));

        return followRequestRepository.findByFollowingId(userId, pageable).stream()
                .map(followRequests -> UserIdResponse.builder()
                        .userId(followRequests.getFollowerId())
                        .build())
                .toList();
    }

    public List<UserIdResponse> getSentFollowRequests(Long userId, int pageNumber) {
        userClient.updateLastSeen(userId);

        Pageable pageable = PageRequest.of(pageNumber-1, 25, Sort.by(Sort.Direction.DESC, "createdTimestamp"));

        return followRequestRepository.findByFollowerId(userId, pageable)
                .stream()
                .map(followRequests -> UserIdResponse.builder()
                        .userId(followRequests.getFollowerId())
                        .build()
                ).toList();
    }

    public FollowRelationResponse acceptFollowRequest(Long userId, Long followerId) {
        userClient.updateLastSeen(userId);

        FollowRequests followRequests = followRequestRepository.findByFollowerIdAndFollowingId(userId, followerId).orElseThrow(() -> new  UserNotFoundException("Follower: " + followerId + " does not exist"));
        FollowRelation followRelation = new FollowRelation();
        followRelation.setCreatedTimestamp(LocalDateTime.now());
        followRelation.setFollowingId(followerId);
        followRelation.setFollowerId(userId);
        FollowRelation savedFollowRelation = followRelationRepository.save(followRelation);
        followRequestRepository.delete(followRequests);

        return  FollowMapper.mapfollowRelationToFollowResponse(
                savedFollowRelation,
                userId,
                followerId,
                true,
                followRelationRepository.existsByFollowerIdAndFollowingId(followerId, userId)
        );


    }


    @Transactional
    public Boolean unfollowUser(Long followerId, Long followingId) {
        userClient.updateLastSeen(followerId);
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
