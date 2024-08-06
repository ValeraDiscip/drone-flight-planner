package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.UserResponse;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.user.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public UserResponse save(RegisterUserRequest registerUserRequest) {
        User userToRegister = UserMapper.mapToUser(registerUserRequest);
        return userService.saveUser(userToRegister);
    }
}
