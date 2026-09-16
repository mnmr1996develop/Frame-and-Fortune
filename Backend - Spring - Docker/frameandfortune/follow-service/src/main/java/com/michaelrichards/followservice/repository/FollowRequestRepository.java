package com.michaelrichards.followservice.repository;

import com.michaelrichards.followservice.entity.FollowRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRequestRepository extends JpaRepository<FollowRequests, Long> {


}
