package org.example.service;

import org.example.dto.FlightPossibilityResult;
import org.example.dto.client.Parameter;

public interface WeatherApiService {
    FlightPossibilityResult evaluateFlightPossibility(Parameter parameters);
}
