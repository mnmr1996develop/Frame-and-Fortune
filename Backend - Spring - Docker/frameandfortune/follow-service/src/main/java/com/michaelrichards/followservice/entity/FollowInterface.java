package com.michaelrichards.followservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class FollowInterface {

    @Column(name = "follower_id", nullable = false, updatable = false)
    private Long followerId;

    @Column(name = "following_id", nullable = false,  updatable = false)
    private Long followingId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTimestamp;
}
