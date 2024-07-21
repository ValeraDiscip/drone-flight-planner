package org.example.dto.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Current {
    private Condition condition;
    //или лучше сделать тип данных String? чтобы не было такого иногда "humidity": 65.0, "pressure_mb": 1007.0, или на это все равно?
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
