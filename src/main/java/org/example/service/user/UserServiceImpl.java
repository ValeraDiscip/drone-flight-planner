package org.example.service.user;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userDao.getUserByLogin(username);

        return new org.springframework.security.core.userdetails.User(foundUser.getLogin(), foundUser.getPassword(),
                new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }
}
