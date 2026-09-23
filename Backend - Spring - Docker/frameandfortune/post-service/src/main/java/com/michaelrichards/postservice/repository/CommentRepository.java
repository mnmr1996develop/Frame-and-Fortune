package com.michaelrichards.postservice.repository;

import com.michaelrichards.postservice.entity.Comment;
import com.michaelrichards.postservice.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {



    List<Comment> findByUserId(Long userId);

    long countByPostId(Long postId);

    List<Comment> findByPostId(Long postId, Pageable pageable);

}
