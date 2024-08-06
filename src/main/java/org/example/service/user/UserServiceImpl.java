package org.example.service.user;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.response.UserResponse;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userDao.getUserByUsername(username);

        if (foundUser == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    public UserResponse saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userDao.saveUser(user);
        return UserMapper.mapToUserResponse(savedUser);
    }
}
