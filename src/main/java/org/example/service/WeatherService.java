package org.example.service;

import org.example.dto.FlightPossibilityResult;

public interface WeatherService {
    FlightPossibilityResult evaluateFlightPossibility(Integer userId);
}
