package com.michaelrichards.followservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =  {"follower_id", "follower_id"})})
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequests extends FollowInterface{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
