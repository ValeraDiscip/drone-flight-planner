package org.example.service;

import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.WeatherForecast;

public interface WeatherApiClient {
    CurrentWeather getCurrentWeather(String city);
    WeatherForecast getWeatherForecast(String city, String language, Integer dayCount);
}
