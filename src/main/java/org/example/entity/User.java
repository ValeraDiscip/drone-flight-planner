package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String login;
    private String password;
    private Parameter parameters;
}
