package org.example.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadResponse {
    private String message;
}
