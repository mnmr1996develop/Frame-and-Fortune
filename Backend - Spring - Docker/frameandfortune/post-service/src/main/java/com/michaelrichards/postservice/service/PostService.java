package com.michaelrichards.postservice.service;

import com.michaelrichards.postservice.client.UserClient;
import com.michaelrichards.postservice.dto.CommentDTOs.*;
import com.michaelrichards.postservice.entity.Comment;
import com.michaelrichards.postservice.entity.PostLike;
import com.michaelrichards.postservice.entity.Post;
import com.michaelrichards.postservice.exceptions.PageOutOfBoundsException;
import com.michaelrichards.postservice.mapper.CommentMapper;
import com.michaelrichards.postservice.mapper.PostMapper;
import com.michaelrichards.postservice.exceptions.PostNotFoundException;
import com.michaelrichards.postservice.repository.CommentLikeRepository;
import com.michaelrichards.postservice.repository.CommentRepository;
import com.michaelrichards.postservice.repository.LikePostRepository;
import com.michaelrichards.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.michaelrichards.postservice.dto.PostDTOs.*;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final CommentLikeRepository commentLikeRepository;

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found"));
    }

    public PostResponse makePost(CreatePostRequest postRequest, Long userId) {

        userClient.updateLastSeen(userId);

        if (postRequest.title().isBlank() || postRequest.content().isBlank()) {
            throw new RuntimeException("Title and Content required");
        }

        Post newPost = new Post();
        newPost.setTitle(postRequest.title());
        newPost.setContent(postRequest.content());
        newPost.setUserId(userId);
        newPost.setImageUrl(postRequest.imageUrl());
        newPost.setCreatedDate(Instant.now());
        newPost.setLastModifiedDate(Instant.now());
        newPost.setTitle(postRequest.title());

        Post savedPost =  postRepository.save(newPost);
        return toResponse(savedPost, userId);
    }

    public PostResponse getPostById(Long postId, Long requestingUserId) {
        userClient.updateLastSeen(requestingUserId);

        return toResponse(findPostById(postId), requestingUserId);
    }

    @Transactional
    public void deletePostById(Long postId, Long requestingUserId) {
        userClient.updateLastSeen(requestingUserId);
        Post post = findPostById(postId);

        if (!post.getPostId().equals(requestingUserId)) {
            throw new IllegalStateException("User "  + requestingUserId + " is not allowed to delete this post");
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public void like(Long postId, Long userId) {
        userClient.updateLastSeen(userId);
        likePostRepository.findByPostIdAndUserId(postId, userId).ifPresent(postLike -> {
            throw new IllegalStateException("Post with id " + postId + " is already liked by user " + userId);
        });

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLike.setCreatedAt(Instant.now());
        likePostRepository.save(postLike);
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        userClient.updateLastSeen(userId);
        likePostRepository.deleteByPostIdAndUserId(postId, userId);
    }

    public List<PostResponse> getPostByUser(Long userId, Integer pageNumber, Integer pageSize, Long requestingUserId) {
        userClient.updateLastSeen(requestingUserId);
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        return postRepository.findByUserId(userId, pageable)
                .stream()
                .map(post -> toResponse(post, requestingUserId))
                .toList();
    }

    private PostResponse toResponse(Post post, Long requestingUserId) {
        long likeCount = likePostRepository.countByPostId(post.getPostId());
        long commentCount = commentRepository.countByPostId(post.getPostId());
        boolean likedByMe = requestingUserId != null &&
            likePostRepository.findByPostIdAndUserId(post.getPostId(), requestingUserId).isPresent();
        return PostMapper.mapPostToResponse(post, likeCount, commentCount, likedByMe);
    }

    private CommentResponse toResponse(Comment comment, Long requestingUserId) {
        long likeCount = commentRepository.countByPostId(comment.getPostId());
        boolean likedByMe = requestingUserId != null && commentLikeRepository.findByCommentIdAndUserId(comment.getCommentId(), requestingUserId).isPresent();

        return CommentMapper.mapCommentToCommentResponse(comment, likeCount, likedByMe);
    }


    public List<PostResponse> getPostByUsers(List<Long> userIds, Integer pageNumber, Integer pageSize, Long requestingUserId) {
        userClient.updateLastSeen(requestingUserId);
        if (userIds.isEmpty()) {
            throw new RuntimeException("User id list is empty");
        }
        if (pageNumber == null || pageNumber <= 0) {
            throw new PageOutOfBoundsException("Invalid page number");
        }
        if (pageSize == null || pageSize <= 0) {
            throw new PageOutOfBoundsException("Invalid page size");
        }

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize,  Sort.by("createdAt").descending());

        return postRepository.findByUserIdIn(userIds, pageable).stream()
                .map(post -> toResponse(post, requestingUserId))
                .toList();

    }

    public CommentResponse addComment(Long postId, CommentRequest commentRequest, Long requestingUserId) {
        userClient.updateLastSeen(requestingUserId);
        findPostById(postId);
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(requestingUserId);
        comment.setContent(commentRequest.content());
        Comment savedComment = commentRepository.save(comment);
        return toResponse(savedComment, requestingUserId);
    }
}
