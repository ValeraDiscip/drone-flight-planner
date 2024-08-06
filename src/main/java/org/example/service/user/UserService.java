package org.example.service.user;

import org.example.dto.response.UserResponse;
import org.example.entity.User;

public interface UserService {
   UserResponse saveUser(User user);
}
