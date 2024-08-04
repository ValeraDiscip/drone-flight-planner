package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.RegisterRequest;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.user.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public String save(RegisterRequest registerRequest) {
        User userToRegister = UserMapper.mapToUser(registerRequest);
        userService.saveUser(userToRegister);
        return "Пользователь успешно зарегистрирован";
    }
}
