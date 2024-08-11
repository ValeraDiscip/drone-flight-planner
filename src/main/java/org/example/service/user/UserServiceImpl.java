package org.example.service.user;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightPlannerUser;
import org.example.dto.response.UserResponse;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return new FlightPlannerUser(foundUser.getUsername(), foundUser.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")), foundUser.getId());
    }

    public UserResponse saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userDao.saveUser(user);
        return UserMapper.mapToUserResponse(savedUser);
    }
}
