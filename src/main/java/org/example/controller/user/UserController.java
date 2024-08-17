package org.example.controller.user;

import org.example.dto.FlightPlannerUser;
import org.example.dto.request.SaveEmailRequest;
import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("user")
public interface UserController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    UserResponse saveUser(@RequestBody RegisterUserRequest registerUserRequest);

    @PostMapping("/saveEmail")
    String saveEmail(@AuthenticationPrincipal FlightPlannerUser flightPlannerUser, @RequestBody SaveEmailRequest saveEmailRequest);
}
