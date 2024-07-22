package org.example.dto.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Current {
    private Condition condition;

    @JsonProperty("temp_c")
    private Double temperature;

    @JsonProperty("wind_kph")
    private Double windSpeed;

    @JsonProperty("pressure_mb")
    private Double pressure;

    @JsonProperty("precip_mm")
    private Double precip;
    private Double humidity;

    @JsonProperty("gust_kph")
    private Double windGust;
}
