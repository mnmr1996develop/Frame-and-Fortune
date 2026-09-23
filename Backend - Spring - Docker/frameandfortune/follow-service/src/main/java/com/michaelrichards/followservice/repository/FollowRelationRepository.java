package com.michaelrichards.followservice.repository;

import com.michaelrichards.followservice.entity.FollowRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRelationRepository extends JpaRepository<FollowRelation,Long> {


    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);



    List<FollowRelation> findByFollowingId(Long followingId);



    List<FollowRelation> findByFollowerId(Long followerId);

    long deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowingId(Long followingId);

    long countByFollowerId(Long followerId);


}
