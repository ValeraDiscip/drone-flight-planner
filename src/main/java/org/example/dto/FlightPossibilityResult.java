package org.example.dto;

import lombok.Data;
import org.example.dto.weather.WeatherInfo;

import java.util.List;

@Data
public class FlightPossibilityResult {
    private WeatherInfo weatherInfo;
    private String conclusion;
    private List<String> inappropriateWeatherConditionsInfo;
}
