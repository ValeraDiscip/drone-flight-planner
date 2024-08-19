package org.example.controller.scheduledflight;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlightPlannerUser;
import org.example.dto.request.SaveScheduledFlightRequest;
import org.example.dto.scheduledflight.ScheduledFlightDto;
import org.example.service.ScheduledFlightService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduledFlightControllerImpl implements ScheduledFlightController {
    private final ScheduledFlightService scheduledFlightService;

    @Override
    public ScheduledFlightDto saveScheduledFlight(FlightPlannerUser flightPlannerUser, SaveScheduledFlightRequest saveScheduledFlightRequest) {
       return scheduledFlightService.saveScheduledFlight(flightPlannerUser.getId(), saveScheduledFlightRequest.getTimeOfFlight());
    }
}
