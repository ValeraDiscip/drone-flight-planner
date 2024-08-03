package org.example.controller;

import org.example.dto.FlightPossibilityResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/weather")
public interface WeatherController {
    @GetMapping("/current")
    FlightPossibilityResult evaluateFlightPossibility();
}
