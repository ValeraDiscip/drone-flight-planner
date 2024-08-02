package org.example.dto.weather.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.dto.weather.WeatherInfo;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class HourWeatherForecast extends WeatherInfo {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;
}
