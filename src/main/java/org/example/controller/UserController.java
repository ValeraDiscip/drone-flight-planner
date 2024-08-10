package org.example.controller;

import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("user")
public interface UserController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    UserResponse save(@RequestBody RegisterUserRequest registerUserRequest);
}
