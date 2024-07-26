package org.example.service;

import org.example.dto.FlightPossibilityResult;
import org.example.dto.user.Parameter;

public interface WeatherApiService {
    FlightPossibilityResult evaluateFlightPossibility(Parameter parameters);
}
