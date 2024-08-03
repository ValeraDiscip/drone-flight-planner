package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.FlightDto;
import org.example.entity.Flight;
import org.example.entity.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.Weather;
import org.example.mapper.FlightMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final UserDao userDao;
    private final WeatherApiClient weatherApiClient;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility(Integer userId) {

        Parameter userParameters = userDao.getParameterByUserId(userId);

        if (userParameters == null) {
            return null;
        }

        FlightPossibilityResult flightPossibilityResult = new FlightPossibilityResult();

        CurrentWeather currentWeather = weatherApiClient.getCurrentWeather(userParameters.getLocation());

        flightPossibilityResult.setCurrentWeather(currentWeather);

        List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();

        if (currentWeather.getCurrent().getTemperature() < userParameters.getMinTemperature()) {
            inappropriateWeatherConditionsInfo.add("Минимально допустимая температура для полета = "
                    + userParameters.getMinTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (ниже допустимой на "
                    + (userParameters.getMinTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getTemperature() > userParameters.getMaxTemperature()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая температура для полета = "
                    + userParameters.getMaxTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (выше допустимой на "
                    + (userParameters.getMaxTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getWindSpeed() > userParameters.getMaxWindSpeed()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая скорость ветра для полета = "
                    + userParameters.getMaxWindSpeed()
                    + ". Текущая скорость ветра = " + currentWeather.getCurrent().getWindSpeed()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getWindSpeed() - userParameters.getMaxWindSpeed()) + ")");
        }

        if (currentWeather.getCurrent().getWindGust() > userParameters.getMaxWindGust()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимые порывы ветра для полета = "
                    + userParameters.getMaxWindGust()
                    + ". Текущие порывы ветра = " + currentWeather.getCurrent().getWindGust()
                    + " (выше допустимых на "
                    + (currentWeather.getCurrent().getWindGust() - userParameters.getMaxWindGust()) + ")");
        }

        if (currentWeather.getCurrent().getHumidity() > userParameters.getMaxHumidity()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая влажность для полета = "
                    + userParameters.getMaxHumidity()
                    + ". Текущая влажность = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getHumidity() - userParameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPrecip() > userParameters.getMaxPrecip()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое кол-во осадков для полета = "
                    + userParameters.getMaxHumidity()
                    + ". Текущее кол-во осадков = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getHumidity() - userParameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPressure() > userParameters.getMaxPressure()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое давление для полета = "
                    + userParameters.getMaxPressure()
                    + ". Текущее давление = " + currentWeather.getCurrent().getPressure()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getPressure() - userParameters.getMaxPressure()) + ")");
        }

        if (!inappropriateWeatherConditionsInfo.isEmpty()) {

            flightPossibilityResult.setConclusion("В настоящий момент полет не рекомендуется. " +
                    "Значения погодных условий не соответствуют выставленным параметрам.");

            flightPossibilityResult.setInappropriateWeatherConditionsInfo(inappropriateWeatherConditionsInfo);

            return flightPossibilityResult;
        }

        flightPossibilityResult.setConclusion("В настоящий момент полет является безопасным!");

        return flightPossibilityResult;
    }

    public FlightDto saveFlightAndWeather(Integer userId, LocalDateTime timeOfFlight, Boolean successful) {
        Parameter parameters = userDao.getParameterByUserId(userId);

        LocalDate localDate = LocalDate.of(timeOfFlight.getYear(), timeOfFlight.getMonth(), timeOfFlight.getDayOfMonth());

        Weather weather = weatherApiClient.getWeatherHistory(parameters.getLocation(), parameters.getLanguage(), timeOfFlight.getHour(), localDate);

        HourWeatherForecast weatherDuringTheFlight = weather.getForecast().getDayWeatherForecast().get(0).getHourWeatherForecast().get(0);

        Flight flight = new Flight();
        flight.setUserId(userId);
        flight.setTimeOfFlight(timeOfFlight);
        flight.setSuccessful(successful);
        flight.setHourWeatherForecast(weatherDuringTheFlight);

        userDao.saveFlightAndWeather(flight);

        return FlightMapper.flightToFlightDto(flight);
    }
}
