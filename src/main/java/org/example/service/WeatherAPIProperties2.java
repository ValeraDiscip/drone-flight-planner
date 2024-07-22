package org.example.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "weather-api")
public class WeatherAPIProperties2 {
    private String url;
    private String key;
}
