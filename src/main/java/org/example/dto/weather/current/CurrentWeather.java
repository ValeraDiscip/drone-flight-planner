package org.example.dto.weather.current;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentWeather {
    private Location location;
    private Current current;
}
