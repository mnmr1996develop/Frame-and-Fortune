package com.michaelrichards.postservice.controller;


import com.michaelrichards.postservice.dto.CommentDTOs.*;
import com.michaelrichards.postservice.dto.PostDTOs.*;
import com.michaelrichards.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {


    private final PostService postService;

    private final Long requestingUserId = 2L;
    //TODO: implement authentication principle when keycloak is up and replace requestingUserId with an actual ID

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest postRequest) {
        return ResponseEntity.ok().body(postService.makePost(postRequest, requestingUserId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPosts(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPostById(postId, requestingUserId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePosts(@PathVariable Long postId) {
        postService.deletePostById(postId, requestingUserId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable("userId") Long userId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResponseEntity.ok().body(postService.getPostByUser(userId, pageNumber, pageSize, requestingUserId));
    }

    @GetMapping("/by-users")
    public ResponseEntity<List<PostResponse>> byAuthors(@RequestParam List<Long> userIds,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        List<PostResponse> posts = postService.getPostByUsers(userIds, pageNumber, pageSize, requestingUserId);
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postService.like(postId, requestingUserId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        postService.unlike(postId, requestingUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> post(@RequestBody CommentRequest commentRequest, @PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.addComment(postId, commentRequest, requestingUserId));
    }



}
