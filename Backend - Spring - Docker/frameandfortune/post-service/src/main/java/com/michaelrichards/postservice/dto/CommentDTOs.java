package com.michaelrichards.postservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class CommentDTOs {
    public record CommentRequest(
            @NotBlank String content
    ) {
    }

    @Builder
    public record CommentResponse(
            Long commentId,
            Long postId,
            Long userId,
            String content,
            Long likes,
            Boolean isLikedByMe
    ) {
    }
}
