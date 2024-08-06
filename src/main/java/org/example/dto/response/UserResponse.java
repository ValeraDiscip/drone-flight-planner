package org.example.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.entity.Parameter;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private Parameter parameters;
}
