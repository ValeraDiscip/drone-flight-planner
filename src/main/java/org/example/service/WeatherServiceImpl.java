package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.ClientDao;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.WeatherForecast;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final ClientDao clientDao;
    private final WeatherApiClient weatherApiClient;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility(Integer clientID) {

        Parameter clientParameters = clientDao.getParameterByClientId(clientID);

        if (clientParameters == null) {
            return null;
        }

        FlightPossibilityResult flightPossibilityResult = new FlightPossibilityResult();

        CurrentWeather currentWeather = weatherApiClient.getCurrentWeather(clientParameters.getLocation());

        flightPossibilityResult.setCurrentWeather(currentWeather);

        List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();

        if (currentWeather.getCurrent().getTemperature() < clientParameters.getMinTemperature()) {
            inappropriateWeatherConditionsInfo.add("Минимально допустимая температура для полета = "
                    + clientParameters.getMinTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (ниже допустимой на "
                    + (clientParameters.getMinTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getTemperature() > clientParameters.getMaxTemperature()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая температура для полета = "
                    + clientParameters.getMaxTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (выше допустимой на "
                    + (clientParameters.getMaxTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getWindSpeed() > clientParameters.getMaxWindSpeed()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая скорость ветра для полета = "
                    + clientParameters.getMaxWindSpeed()
                    + ". Текущая скорость ветра = " + currentWeather.getCurrent().getWindSpeed()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getWindSpeed() - clientParameters.getMaxWindSpeed()) + ")");
        }

        if (currentWeather.getCurrent().getWindGust() > clientParameters.getMaxWindGust()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимые порывы ветра для полета = "
                    + clientParameters.getMaxWindGust()
                    + ". Текущие порывы ветра = " + currentWeather.getCurrent().getWindGust()
                    + " (выше допустимых на "
                    + (currentWeather.getCurrent().getWindGust() - clientParameters.getMaxWindGust()) + ")");
        }

        if (currentWeather.getCurrent().getHumidity() > clientParameters.getMaxHumidity()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая влажность для полета = "
                    + clientParameters.getMaxHumidity()
                    + ". Текущая влажность = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getHumidity() - clientParameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPrecip() > clientParameters.getMaxPrecip()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое кол-во осадков для полета = "
                    + clientParameters.getMaxHumidity()
                    + ". Текущее кол-во осадков = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getHumidity() - clientParameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPressure() > clientParameters.getMaxPressure()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое давление для полета = "
                    + clientParameters.getMaxPressure()
                    + ". Текущее давление = " + currentWeather.getCurrent().getPressure()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getPressure() - clientParameters.getMaxPressure()) + ")");
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

    public void addFlight(Integer clientId, LocalDateTime timeOfFlight, Boolean successful) {
        Parameter parameters = clientDao.getParameterByClientId(clientId);

        LocalDate localDate = LocalDate.of(timeOfFlight.getYear(), timeOfFlight.getMonth(), timeOfFlight.getDayOfMonth());

        WeatherForecast weatherForecast = weatherApiClient.getWeatherHistory(parameters.getLocation(), parameters.getLanguage(), timeOfFlight.getHour(), localDate);

        HourWeatherForecast weatherDuringTheFlight = weatherForecast.getForecast().getDayWeatherForecast().get(0).getHourWeatherForecast().get(0);

        Flight flight = new Flight();
        flight.setClientId(clientId);
        flight.setTimeOfFlight(timeOfFlight);
        flight.setSuccessful(successful);
        flight.setHourWeatherForecast(weatherDuringTheFlight);

        clientDao.insertFlight(flight);
    }
}
