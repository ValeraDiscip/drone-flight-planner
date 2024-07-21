package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.WeatherForecast;
import org.example.service.WeatherAPIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("weather")
public class TestController {
    private final WeatherAPIService weatherAPIService;

    @GetMapping("/current")
    public CurrentWeather getWeather(@RequestParam("city") String city, @RequestParam(value = "language", required = false) String language) {
        return weatherAPIService.getCurrentWeather(city, language);
    }

    @GetMapping("/forecast")
    public WeatherForecast getWeatherForecast(@RequestParam("city") String city, @RequestParam(value = "language", required = false) String language, @RequestParam(value = "dayCount", required = false) Integer dayCount) {
        return weatherAPIService.getWeatherForecast(city, language, dayCount);
    }
}
