package com.michaelrichards.userservice.mapper;

import com.michaelrichards.userservice.dto.UserResponse;
import com.michaelrichards.userservice.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .lastSeen(user.getLastSeen())
                .isOnline(user.getIsOnline())
                .build();
    }


}
