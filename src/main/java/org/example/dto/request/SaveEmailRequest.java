package org.example.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SaveEmailRequest {
    @Email(message = "Please provide a valid email address", regexp = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")
    private String email;
}
