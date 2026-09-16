package com.michaelrichards.followservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =  {"follower_id", "follower_id"})})
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id", nullable = false, updatable = false)
    private Long followerId;

    @Column(name = "following_id", nullable = false,  updatable = false)
    private Long followingId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTimestamp;
}
