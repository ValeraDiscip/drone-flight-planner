package org.example.dto.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Condition {
    @JsonProperty("text")
    private String weatherConditionText;

    @JsonProperty("icon")
    private String weatherIconUrl;
}
