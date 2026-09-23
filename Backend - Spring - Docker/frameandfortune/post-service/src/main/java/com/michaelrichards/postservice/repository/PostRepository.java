package com.michaelrichards.postservice.repository;

import com.michaelrichards.postservice.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findByUserId(Long userId, Pageable pageable);

    List<Post> findByUserIdIn(Collection<Long> userIds, Pageable pageable);

}
