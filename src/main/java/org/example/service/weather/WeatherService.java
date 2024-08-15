package org.example.service.weather;

import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.entity.Parameter;

import java.time.LocalDateTime;

public interface WeatherService {
    FlightPossibilityResult evaluateCurrentFlightPossibility(int userId);

    FlightDto saveFlightAndWeather(int userId, LocalDateTime timeOfFlight, boolean successful);

    FlightPossibilityResult evaluateFutureFlightPossibility(HourWeatherForecast hourWeatherForecast, Parameter userParameter);
}
