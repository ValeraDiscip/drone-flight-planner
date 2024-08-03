package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;
import org.example.service.WeatherService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class FlightControllerImpl implements FlightController {
    private final WeatherService weatherService;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility() {
        return weatherService.evaluateFlightPossibility(1);
    }

    @Override
    public FlightDto addFlight(LocalDateTime timeOfFlight, Boolean successful) {
        return weatherService.saveFlightAndWeather(1, timeOfFlight, successful);
    }
}
