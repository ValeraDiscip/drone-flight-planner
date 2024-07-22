package org.example.dto.weather.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DayWeatherForecast {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Astronomy astro;

    @JsonProperty("hour")
    private List<HourWeatherForecast> hourWeatherForecast;
}
