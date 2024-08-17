package org.example.mapper;

import org.example.dto.scheduledflight.ScheduledFlightDto;
import org.example.entity.ScheduledFlight;

public class ScheduledFlightMapper {
    public static ScheduledFlightDto mapToScheduledFlightDto(ScheduledFlight scheduledFlight) {
        return ScheduledFlightDto.builder()
                .flightId(scheduledFlight.getFlightId())
                .userId(scheduledFlight.getUserId())
                .timeOfFlight(scheduledFlight.getTimeOfFlight())
                .lastFlightPossibilityDecision(scheduledFlight.isLastFlightPossibilityDecision())
                .build();
    }
}
