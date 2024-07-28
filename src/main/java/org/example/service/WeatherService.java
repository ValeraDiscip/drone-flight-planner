package org.example.service;

import org.example.dto.FlightPossibilityResult;

import java.time.LocalDateTime;

public interface WeatherService {
    FlightPossibilityResult evaluateFlightPossibility(Integer userId);

    void addFlight(Integer clientId, LocalDateTime timeOfFlight, Boolean successful);
}
