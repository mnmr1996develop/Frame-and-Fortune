package com.michaelrichards.followservice.repository;

import com.michaelrichards.followservice.entity.FollowRequests;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FollowRequestRepository extends JpaRepository<FollowRequests, Long> {


    Optional<FollowRequests> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<FollowRequests> findByFollowerId(Long followerId, Pageable pageable);

    List<FollowRequests> findByFollowingId(Long followingId, Pageable pageable);


}
