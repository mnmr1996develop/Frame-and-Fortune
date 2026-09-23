package com.michaelrichards.postservice.mapper;

import com.michaelrichards.postservice.entity.Post;
import com.michaelrichards.postservice.dto.PostDTOs.*;

public class PostMapper {

    private PostMapper() {}

    public static PostResponse mapPostToResponse(Post post, Long likeCount, Long commentCount, Boolean likedByMe) {
        return PostResponse.builder()
                .authorId(post.getUserId())
                .postId(post.getPostId())
                .content(post.getContent())
                .title(post.getTitle())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .likedByMe(likedByMe)
                .createdAt(post.getCreatedDate())
                .build();
    }
}
