package org.example.dto.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Current {
    private Condition condition;

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
