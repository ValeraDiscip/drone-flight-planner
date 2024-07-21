package org.example.dto.weather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Forecast {
    @JsonProperty("forecastday")
    private List<DayWeatherForecast> dayWeatherForecast;
}
