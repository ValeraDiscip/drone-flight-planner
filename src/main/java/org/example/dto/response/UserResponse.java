package org.example.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.entity.Parameter;

@Data
@Builder
public class UserResponse {
    private int id;
    private String username;
    private String email;
    private Parameter parameters;
}
