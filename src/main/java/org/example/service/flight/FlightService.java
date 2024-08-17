package org.example.service.flight;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightPossibilityResult;
import org.example.entity.ScheduledFlight;
import org.example.service.weather.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final WeatherService weatherService;
    private final UserDao userDao;

    public ScheduledFlight addScheduledFlight(int userId, LocalDateTime timeOfFlight) {
       FlightPossibilityResult flightPossibilityResult = weatherService.evaluateFutureFlightPossibility(userId, timeOfFlight);
       boolean possibilityDecision = flightPossibilityResult.getInappropriateWeatherConditionsInfo().isEmpty();

        ScheduledFlight scheduledFlight = ScheduledFlight.builder()
                .userId(userId)
                .timeOfFlight(timeOfFlight)
                .lastFlightPossibilityDecision(possibilityDecision)
                .build();

       return userDao.saveScheduledFlight(scheduledFlight);
    }
}
