package org.example.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.Weather;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiClientImpl implements WeatherApiClient {
    private final WeatherApiProperties weatherAPIProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public CurrentWeather getCurrentWeather(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties.getUrl() + "/current.json" + "?q={city}",
                HttpMethod.GET,
                request,
                String.class,
                city
        );

        try {
            return objectMapper.readValue(response.getBody(), CurrentWeather.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Weather getWeatherForecast(String city, String language, Integer dayCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties.getUrl() + "/forecast.json" + "?q={city}&language={language}&days={dayCount}",
                HttpMethod.GET,
                request,
                String.class,
                city,
                language,
                dayCount
        );
        try {
            return objectMapper.readValue(response.getBody(), Weather.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Weather getWeatherHistory(String city, String language, Integer hour, LocalDate localDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties.getUrl() + "/history.json" + "?q={city}&lang={language}&hour={hour}&dt={localDate}",
                HttpMethod.GET,
                request,
                String.class,
                city,
                language,
                hour,
                localDate
        );
        try {
            return objectMapper.readValue(response.getBody(), Weather.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
