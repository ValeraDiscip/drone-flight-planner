package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlightDto;
import org.example.dto.FlightPlannerUser;
import org.example.dto.FlightPossibilityResult;
import org.example.service.weather.WeatherService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class FlightControllerImpl implements FlightController {
    private final WeatherService weatherService;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility(FlightPlannerUser flightPlannerUser) {
        return weatherService.evaluateCurrentFlightPossibility(flightPlannerUser.getId());
    }

    @Override
    public FlightDto addFlight(FlightPlannerUser flightPlannerUser, LocalDateTime timeOfFlight, Boolean successful) {
        return weatherService.saveFlightAndWeather(flightPlannerUser.getId(), timeOfFlight, successful);
    }
}
