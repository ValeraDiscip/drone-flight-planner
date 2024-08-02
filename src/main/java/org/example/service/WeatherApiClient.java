package org.example.service;

import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.Weather;

import java.time.LocalDate;

public interface WeatherApiClient {
    CurrentWeather getCurrentWeather(String city);

    Weather getWeatherForecast(String city, String language, Integer dayCount);

    Weather getWeatherHistory(String city, String language, Integer hour, LocalDate localDate);
}
