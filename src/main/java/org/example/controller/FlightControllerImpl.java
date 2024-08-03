package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlightPossibilityResult;
import org.example.service.WeatherService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FlightControllerImpl implements FlightController {
    private final WeatherService weatherService;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility() {
        return weatherService.evaluateFlightPossibility(1);
    }
}
