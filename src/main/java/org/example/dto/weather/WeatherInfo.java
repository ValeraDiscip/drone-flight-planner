package org.example.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherInfo {
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
