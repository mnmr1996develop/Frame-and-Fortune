package com.michaelrichards.postservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Instant;

public class PostDTOs {
    public record CreatePostRequest(
            @NotBlank String imageUrl,
            String title,
            String content
    ){}

    @Builder
    public record PostResponse(
            Long postId,
            Long authorId,
            String imageUrl,
            String content,
            String title,
            Long likeCount,
            Long commentCount,
            Boolean likedByMe,
            Instant createdAt
    ){}



}
