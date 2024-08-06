package org.example.controller;

import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user")
public interface UserController {

    @PostMapping("/register")
    UserResponse save(@RequestBody RegisterUserRequest registerUserRequest);
}
