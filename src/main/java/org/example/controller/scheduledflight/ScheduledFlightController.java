package org.example.controller.scheduledflight;


import org.example.dto.FlightPlannerUser;
import org.example.dto.request.SaveScheduledFlightRequest;
import org.example.dto.scheduledflight.ScheduledFlightDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("scheduledFlight")
public interface ScheduledFlightController {
    @PostMapping("/save")
    ScheduledFlightDto saveScheduledFlight(@AuthenticationPrincipal FlightPlannerUser flightPlannerUser, @RequestBody SaveScheduledFlightRequest saveScheduledFlightRequest);
}
