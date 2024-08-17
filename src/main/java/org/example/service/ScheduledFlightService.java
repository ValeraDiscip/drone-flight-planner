package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.scheduledflight.ScheduledFlightDto;
import org.example.entity.ScheduledFlight;
import org.example.mapper.ScheduledFlightMapper;
import org.example.service.mail.FlightPlannerEmailService;
import org.example.service.weather.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScheduledFlightService {
    private final UserDao userDao;
    private final WeatherService weatherService;
    private final FlightPlannerEmailService flightPlannerEmailService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void evaluateFlightPossibilityOnPlannedDay() {

        List<ScheduledFlight> scheduledFlights = userDao.getScheduledFlights();

        for (ScheduledFlight scheduledFlight : scheduledFlights) {
            FlightPossibilityResult flightPossibilityResult = weatherService.evaluateFutureFlightPossibility(scheduledFlight.getUserId(), scheduledFlight.getTimeOfFlight());

            if (flightPossibilityResult != null && flightPossibilityResult.getInappropriateWeatherConditionsInfo().isEmpty() != scheduledFlight.isLastFlightPossibilityDecision()) {
                userDao.updateLastFlightPossibilityDecision(scheduledFlight.getUserId(), !scheduledFlight.isLastFlightPossibilityDecision());
                String toEmail = userDao.getEmailByUserId(scheduledFlight.getUserId());
                if (toEmail != null) {
                    flightPlannerEmailService.sendEmail(toEmail, "ИЗМЕНЕНЕНИЕ ПОГОДНЫХ УСЛОВИЙ НА ЗАПЛАНИРОВАННЫЙ ДЕНЬ ПОЛЕТА", flightPossibilityResult.toString());
                }
            }
        }
    }

    public ScheduledFlightDto saveScheduledFlight(Integer userId, LocalDateTime timeOfFlight) {
       FlightPossibilityResult flightPossibilityResult = weatherService.evaluateFutureFlightPossibility(userId, timeOfFlight);

       if (flightPossibilityResult == null) {
           return null;
       }

       boolean flightPossibilityDecision = flightPossibilityResult.getInappropriateWeatherConditionsInfo().isEmpty();
       ScheduledFlight scheduledFlightForSave = ScheduledFlight.builder()
               .userId(userId)
               .timeOfFlight(timeOfFlight)
               .lastFlightPossibilityDecision(flightPossibilityDecision)
               .build();

      ScheduledFlight savedScheduledFlight = userDao.saveScheduledFlight(scheduledFlightForSave);
      return ScheduledFlightMapper.mapToScheduledFlightDto(savedScheduledFlight);
    }
}

