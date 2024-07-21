package org.example.dto.weather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "weather-api")
public class WeatherAPIProperties {
    private String url;
    private String key;
}
