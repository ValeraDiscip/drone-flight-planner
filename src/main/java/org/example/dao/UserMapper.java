package org.example.dao;

import org.example.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .build();
    }
}
