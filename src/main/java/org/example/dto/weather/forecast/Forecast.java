package org.example.dto.weather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Forecast {
    @JsonProperty("forecastday")
    private List<DayWeatherForecast> dayWeatherForecast;
}
