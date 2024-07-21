package org.example.dto.weather.current;

import lombok.Data;

@Data
public class CurrentWeather {
    private Location location;
    private Current current;
}
