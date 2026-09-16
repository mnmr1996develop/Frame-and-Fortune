package com.michaelrichards.followservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "following_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @Column(name = "following_id", nullable = false)
    private Long followingId;

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

}
