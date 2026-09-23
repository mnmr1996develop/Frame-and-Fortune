package com.michaelrichards.postservice.repository;

import com.michaelrichards.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<PostLike,Long> {

    long countByPostId(Long postId);

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    long deleteByPostIdAndUserId(Long postId, Long userId);


}
