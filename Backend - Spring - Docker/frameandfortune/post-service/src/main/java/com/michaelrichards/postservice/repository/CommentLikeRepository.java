package com.michaelrichards.postservice.repository;


import com.michaelrichards.postservice.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    long countByCommentId(Long commentId);

    long deleteByCommentIdAndUserId(Long commentId, Long userId);

    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
}
