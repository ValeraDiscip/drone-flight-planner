package org.example.dto.client;

import lombok.Data;
import org.example.dto.weather.forecast.HourWeatherForecast;

import java.time.LocalDateTime;

@Data
public class Flight {
    private Integer id;
    private Integer clientId;
    private LocalDateTime timeOfFlight;
    private Boolean successful;
    private HourWeatherForecast hourWeatherForecast;
}
