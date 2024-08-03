package org.example.dto;

import lombok.Data;
import org.example.dto.weather.current.CurrentWeather;

import java.util.List;

@Data
public class FlightPossibilityResult {
    private CurrentWeather currentWeather;
    private String conclusion;
    private List<String> inappropriateWeatherConditionsInfo;
}
