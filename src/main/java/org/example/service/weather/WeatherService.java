package org.example.service.weather;

import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;

import java.time.LocalDateTime;

public interface WeatherService {
    FlightPossibilityResult evaluateCurrentFlightPossibility(int userId);

    FlightDto saveFlightAndWeather(int userId, LocalDateTime timeOfFlight, boolean successful);

    FlightPossibilityResult evaluateFutureFlightPossibility(int userId, LocalDateTime timeOfFlight);
}
