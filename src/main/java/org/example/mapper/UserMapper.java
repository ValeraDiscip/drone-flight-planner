package org.example.mapper;

import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.UserResponse;
import org.example.entity.User;

public class UserMapper {

    public static User mapToUser(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .parameters(user.getParameters())
                .build();
    }
}
