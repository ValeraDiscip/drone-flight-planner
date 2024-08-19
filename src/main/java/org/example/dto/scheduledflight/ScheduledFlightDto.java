package org.example.dto.scheduledflight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduledFlightDto {
    private int flightId;
    private int userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timeOfFlight;
    private boolean lastFlightPossibilityDecision;
}
