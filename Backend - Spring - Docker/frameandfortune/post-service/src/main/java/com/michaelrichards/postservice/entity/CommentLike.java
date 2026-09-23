package com.michaelrichards.postservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_likes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "comment_id"})})
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private Long commentId;

    private Long userId;

    @CreatedDate
    private Instant createdDate;

}
