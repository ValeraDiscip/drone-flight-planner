package org.example.dto.weather.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HourWeatherForecast {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    @JsonProperty("temp_c")
    private Short temperature;

    @JsonProperty("wind_kph")
    private Short windSpeed;

    @JsonProperty("pressure_mb")
    private Short pressure;
    private Short humidity;

    @JsonProperty("gust_kph")
    private Short windGust;
}


