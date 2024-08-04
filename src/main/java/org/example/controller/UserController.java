package org.example.controller;

import org.example.dto.request.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public interface UserController {

    @PostMapping("/register")
    String save(@RequestBody RegisterRequest registerRequest);
}
