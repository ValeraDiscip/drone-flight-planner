package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.WeatherForecast;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherAPIService {
    private final WeatherAPIProperties2 weatherAPIProperties2;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CurrentWeather getCurrentWeather(String city, String language) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties2.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties2.getUrl() + "/current.json" + "?q={city}&lang={language}",
                HttpMethod.GET,
                request,
                String.class,
                city,
                language
        );

        try {
            return objectMapper.readValue(response.getBody(), CurrentWeather.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherForecast getWeatherForecast(String city, String language, Integer dayCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties2.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties2.getUrl() + "/forecast.json" + "?q={city}&language={language}&days={dayCount}",
                HttpMethod.GET,
                request,
                String.class,
                city,
                language,
                dayCount
        );
        try {
            return objectMapper.readValue(response.getBody(), WeatherForecast.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
