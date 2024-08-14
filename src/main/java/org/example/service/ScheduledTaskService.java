package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.ScheduledFlight;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.forecast.Weather;
import org.example.entity.Parameter;
import org.example.service.weather.WeatherApiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final UserDao userDao;
    private final WeatherApiClient weatherApiClient;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void evaluateFlightPossibilityOnPlannedDay() {
        ScheduledFlight scheduledFlight = userDao.getScheduledFlightByUserId(1);
        if (scheduledFlight != null && scheduledFlight.getDepartureTime().isAfter(LocalDateTime.now())) {
            Parameter parameter = userDao.getParameterByUserId(1);

            Weather weatherForecast = weatherApiClient.getWeatherForecast(parameter.getLocation(),
                    parameter.getLanguage(), 1, scheduledFlight.getDepartureTime().toLocalDate());

            HourWeatherForecast hourWeatherForecast = weatherForecast.getForecast().getDayWeatherForecast().get(0)
                    .getHourWeatherForecast().get(scheduledFlight.getDepartureTime().getHour());


        }
    }
}
