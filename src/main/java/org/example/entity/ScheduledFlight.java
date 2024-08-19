package org.example.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduledFlight {
    private int flightId;
    private int userId;
    private LocalDateTime timeOfFlight;
    private boolean lastFlightPossibilityDecision;
}
