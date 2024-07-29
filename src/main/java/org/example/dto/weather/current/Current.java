package org.example.dto.weather.current;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.dto.weather.WeatherInfo;

@EqualsAndHashCode(callSuper = true)
@Data
public class Current extends WeatherInfo {
    private Condition condition;
}
