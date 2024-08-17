package org.example.service.weather;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.weather.WeatherInfo;
import org.example.dto.weather.current.Current;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.forecast.Weather;
import org.example.entity.Flight;
import org.example.entity.Parameter;
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
    public FlightPossibilityResult evaluateCurrentFlightPossibility(int userId) {
        Parameter userParameters = userDao.getParameterByUserId(userId);

        if (userParameters == null) {
            return null;
        }

        CurrentWeather currentWeather = weatherApiClient.getCurrentWeather(userParameters.getLocation());
        Current current = currentWeather.getCurrent();

        return evaluateFlightPossibility(current, userParameters);
    }


    public FlightPossibilityResult evaluateFutureFlightPossibility(int userId, LocalDateTime timeOfFlight) {

        Parameter userParameter = userDao.getParameterByUserId(userId);

        if (userParameter == null) {
            return null;
        }

        Weather weatherForecast = weatherApiClient.getWeatherForecast(userParameter.getLocation(),
                userParameter.getLanguage(), 1, timeOfFlight.toLocalDate());

        HourWeatherForecast hourWeatherForecast = weatherForecast.getForecast().getDayWeatherForecast().get(0)
                .getHourWeatherForecast().get(timeOfFlight.getHour());

        return evaluateFlightPossibility(hourWeatherForecast, userParameter);
    }


    private FlightPossibilityResult evaluateFlightPossibility(WeatherInfo weatherInfo, Parameter userParameter) {
        if (userParameter == null) {
            return null;
        }

        FlightPossibilityResult flightPossibilityResult = new FlightPossibilityResult();
        flightPossibilityResult.setWeatherInfo(weatherInfo);
        List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();

        if (weatherInfo.getTemperature() < userParameter.getMinTemperature()) {
            inappropriateWeatherConditionsInfo.add("Минимально допустимая температура для полета = "
                    + userParameter.getMinTemperature()
                    + ". Температура по прогнозу = " + weatherInfo.getTemperature()
                    + " (ниже допустимой на "
                    + (userParameter.getMinTemperature() - weatherInfo.getTemperature()) + ")");
        }

        if (weatherInfo.getTemperature() > userParameter.getMaxTemperature()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая температура для полета = "
                    + userParameter.getMaxTemperature()
                    + ". Температура по прогнозу = " + weatherInfo.getTemperature()
                    + " (выше допустимой на "
                    + (weatherInfo.getTemperature() - userParameter.getMaxTemperature()) + ")");
        }

        if (weatherInfo.getWindSpeed() > userParameter.getMaxWindSpeed()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая скорость ветра для полета = "
                    + userParameter.getMaxWindSpeed()
                    + ". Скорость ветра по прогнозу = " + weatherInfo.getWindSpeed()
                    + " (выше допустимой на "
                    + (weatherInfo.getWindSpeed() - userParameter.getMaxWindSpeed()) + ")");
        }

        if (weatherInfo.getWindGust() > userParameter.getMaxWindGust()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимые порывы ветра для полета = "
                    + userParameter.getMaxWindGust()
                    + ". Порывы ветра по прогнозу = " + weatherInfo.getWindGust()
                    + " (выше допустимых на "
                    + (weatherInfo.getWindGust() - userParameter.getMaxWindGust()) + ")");
        }

        if (weatherInfo.getHumidity() > userParameter.getMaxHumidity()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая влажность для полета = "
                    + userParameter.getMaxHumidity()
                    + ". Влажность по прогнозу = " + weatherInfo.getHumidity()
                    + " (выше допустимой на "
                    + (weatherInfo.getHumidity() - userParameter.getMaxHumidity()) + ")");
        }

        if (weatherInfo.getPrecip() > userParameter.getMaxPrecip()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое кол-во осадков для полета = "
                    + userParameter.getMaxHumidity()
                    + ". Кол-во осадков по прогнозу = " + weatherInfo.getHumidity()
                    + " (выше допустимого на "
                    + (weatherInfo.getHumidity() - userParameter.getMaxHumidity()) + ")");
        }

        if (weatherInfo.getPressure() > userParameter.getMaxPressure()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое давление для полета = "
                    + userParameter.getMaxPressure()
                    + ". Давление по прогнозу = " + weatherInfo.getPressure()
                    + " (выше допустимого на "
                    + (weatherInfo.getPressure() - userParameter.getMaxPressure()) + ")");
        }

        if (!inappropriateWeatherConditionsInfo.isEmpty()) {

            if (weatherInfo instanceof Current) {
                flightPossibilityResult.setConclusion("В настоящий момент полет не рекомендуется. " +
                        "Значения погодных условий не соответствуют выставленным параметрам.");
            } else if (weatherInfo instanceof HourWeatherForecast) {
                flightPossibilityResult.setConclusion(((HourWeatherForecast) weatherInfo).getTime() + "полет не рекомендуется. " +
                        "Значения погодных условий не соответствуют выставленным параметрам.");
            }
        }

        else {
            if (weatherInfo instanceof Current) {
                flightPossibilityResult.setConclusion("В настоящий момент полет является безопасным!");
            }
        }
        flightPossibilityResult.setInappropriateWeatherConditionsInfo(inappropriateWeatherConditionsInfo);

        return flightPossibilityResult;
    }

    public FlightDto saveFlightAndWeather(int userId, LocalDateTime timeOfFlight, boolean successful) {
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
