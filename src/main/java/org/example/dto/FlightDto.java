package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.example.dto.weather.forecast.HourWeatherForecast;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightDto {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timeOfFlight;
    private Boolean successful;
    private HourWeatherForecast hourWeatherForecast;
}
