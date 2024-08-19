package org.example.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BadResponse {
    private List<String> messages;
}
