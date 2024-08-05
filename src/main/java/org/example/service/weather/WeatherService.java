package org.example.service.weather;

import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;

import java.time.LocalDateTime;

public interface WeatherService {
    FlightPossibilityResult evaluateFlightPossibility(Integer userId);

    FlightDto saveFlightAndWeather(Integer userId, LocalDateTime timeOfFlight, Boolean successful);
}
