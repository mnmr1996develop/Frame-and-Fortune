package com.michaelrichards.followservice.repository;

import com.michaelrichards.followservice.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRelationRepository extends JpaRepository<Follow,Long> {


    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);



    List<Follow> findByFollowingId(Long followingId);



    List<Follow> findByFollowerId(Long followerId);

    long deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowingId(Long followingId);

    long countByFollowerId(Long followerId);


}
