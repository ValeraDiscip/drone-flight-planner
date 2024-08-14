package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduledFlight {
    private int flightId;
    private int userId;
    private LocalDateTime departureTime;
}
