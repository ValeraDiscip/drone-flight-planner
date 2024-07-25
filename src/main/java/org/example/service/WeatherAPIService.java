package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.FlightResponse;
import org.example.dto.user.Parameter;
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

    public CurrentWeather getCurrentWeather(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("x-rapidapi-key", weatherAPIProperties2.getKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherAPIProperties2.getUrl() + "/current.json" + "?q={city}",
                HttpMethod.GET,
                request,
                String.class,
                city
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

    public FlightResponse evaluateThePossibilityOfFlight(Parameter parameters) {
        FlightResponse flightResponse = new FlightResponse();

        CurrentWeather currentWeather = getCurrentWeather(parameters.getLocation());

        flightResponse.setCurrentWeather(currentWeather);

        if (currentWeather.getCurrent().getTemperature() < parameters.getMinTemperature()) {
            flightResponse.addInappropriateWeatherConditionInFo("Температура ниже допустимой на "
                    + (parameters.getMinTemperature() - currentWeather.getCurrent().getTemperature()));
        }

        if (currentWeather.getCurrent().getTemperature() > parameters.getMaxTemperature()) {
            flightResponse.addInappropriateWeatherConditionInFo("Температура выше допустимой на "
                    + (currentWeather.getCurrent().getTemperature() - parameters.getMaxTemperature()));
        }

        if (currentWeather.getCurrent().getWindSpeed() > parameters.getMaxWindSpeed()) {
            flightResponse.addInappropriateWeatherConditionInFo("Скорость ветра выше допустимой на "
                    + (currentWeather.getCurrent().getWindSpeed() - parameters.getMaxWindSpeed()));
        }

        if (currentWeather.getCurrent().getWindGust() > parameters.getMaxWindGust()) {
            flightResponse.addInappropriateWeatherConditionInFo("Порывы ветра выше допустимого на "
                    + (currentWeather.getCurrent().getWindGust() - parameters.getMaxWindGust()));
        }

        if (parameters.getMaxHumidity() != null && currentWeather.getCurrent().getHumidity() > parameters.getMaxHumidity()) {
            flightResponse.addInappropriateWeatherConditionInFo("Влажность выше допустимой на "
                    + (currentWeather.getCurrent().getHumidity() - parameters.getMaxHumidity()));
        }

        if (currentWeather.getCurrent().getPressure() > parameters.getMaxPressure()) {
            flightResponse.addInappropriateWeatherConditionInFo("Давление выше допустимого на "
                    + (currentWeather.getCurrent().getPressure() - parameters.getMaxPressure()));

        }

        if (!flightResponse.getInappropriateWeatherConditionsInfo().isEmpty()) {

            flightResponse.setConclusion("В настоящий момент полет не рекомендуется. " +
                    "Значения погодных условий не соответствуют выставленным параметрам.");

            return flightResponse;
        }

        flightResponse.setConclusion("В настоящий момент полет является безопасным!");

        return flightResponse;
    }
}
