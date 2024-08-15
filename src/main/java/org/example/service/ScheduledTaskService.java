package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.ScheduledFlight;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.forecast.Weather;
import org.example.entity.Parameter;
import org.example.service.weather.WeatherApiClient;
import org.example.service.weather.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final UserDao userDao;
    private final WeatherApiClient weatherApiClient;
    private final WeatherService weatherService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void evaluateFlightPossibilityOnPlannedDay() {
        ScheduledFlight scheduledFlight = userDao.getScheduledFlightByUserId(1);

        if (scheduledFlight == null || scheduledFlight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return;
        }

        Parameter userParameter = userDao.getParameterByUserId(1);

        if (userParameter == null) {
            return;
        }

        Weather weatherForecast = weatherApiClient.getWeatherForecast(userParameter.getLocation(),
                userParameter.getLanguage(), 1, scheduledFlight.getDepartureTime().toLocalDate());

        HourWeatherForecast hourWeatherForecast = weatherForecast.getForecast().getDayWeatherForecast().get(0)
                .getHourWeatherForecast().get(scheduledFlight.getDepartureTime().getHour());

        FlightPossibilityResult flightPossibilityResult = weatherService.evaluateFutureFlightPossibility(hourWeatherForecast, userParameter);
    }
}

