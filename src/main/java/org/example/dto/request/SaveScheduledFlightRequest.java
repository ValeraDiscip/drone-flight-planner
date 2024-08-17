package org.example.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaveScheduledFlightRequest {

    @NotNull
    private LocalDateTime timeOfFlight;
}
