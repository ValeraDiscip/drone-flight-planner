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
    private Double temperature;

    @JsonProperty("wind_kph")
    private Double windSpeed;

    @JsonProperty("pressure_mb")
    private Double pressure;
    private Double humidity;

    @JsonProperty("precip_mm")
    private Double precip;

    @JsonProperty("gust_kph")
    private Double windGust;
}


