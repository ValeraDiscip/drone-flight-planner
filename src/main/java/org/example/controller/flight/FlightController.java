package org.example.controller.flight;

import org.example.dto.FlightDto;
import org.example.dto.FlightPlannerUser;
import org.example.dto.FlightPossibilityResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequestMapping("flight")
public interface FlightController {
    @GetMapping("/evaluateCurrentPossibility")
    FlightPossibilityResult evaluateFlightPossibility(@AuthenticationPrincipal FlightPlannerUser flightPlannerUser);

    @PostMapping("/save")
    FlightDto saveFlight(@AuthenticationPrincipal FlightPlannerUser flightPlannerUser, LocalDateTime timeOfFlight, Boolean successful);
}
