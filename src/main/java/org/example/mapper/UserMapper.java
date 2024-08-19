package org.example.mapper;

import org.example.dto.request.RegisterUserRequest;
import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserResponse;
import org.example.entity.User;

public class UserMapper {

    public static User mapToUser(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail())
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .parameters(user.getParameters())
                .build();
    }

    public static User mapToUser(int userId, UpdateUserRequest updateUserRequest) {
        return User.builder()
                .id(userId)
                .username(updateUserRequest.getUsername())
                .password(updateUserRequest.getPassword())
                .email(updateUserRequest.getEmail())
                .build();
    }
}
