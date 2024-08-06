package org.example.service.weather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "weather-api")
public class WeatherApiProperties {
    private String url;
    private String key;
}
