package org.example.mapper;

import org.example.dto.request.RegisterRequest;
import org.example.entity.User;

public class UserMapper {

    public static User mapToUser(RegisterRequest registerRequest) {
        return User.builder()
                .login(registerRequest.getLogin())
                .password(registerRequest.getPassword())
                .build();
    }
}
