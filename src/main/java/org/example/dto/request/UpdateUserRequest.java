package org.example.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(min = 2, max = 20)
    private String username;

    @Size(min = 5, max = 20)
    private String password;

    @Email(message = "Please provide a valid email address", regexp = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")
    private String email;
}
