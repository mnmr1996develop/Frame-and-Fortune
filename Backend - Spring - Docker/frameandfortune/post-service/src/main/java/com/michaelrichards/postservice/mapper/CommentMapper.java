package com.michaelrichards.postservice.mapper;

import com.michaelrichards.postservice.dto.CommentDTOs.*;
import com.michaelrichards.postservice.entity.Comment;

public class CommentMapper {
    private CommentMapper() {}

    public static CommentResponse mapCommentToCommentResponse(Comment comment, Long commentLikes, Boolean isLiked) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .likes(commentLikes)
                .isLikedByMe(isLiked)
                .build();
    }
}
