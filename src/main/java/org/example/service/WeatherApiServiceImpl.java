package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.user.Parameter;
import org.example.dto.weather.current.CurrentWeather;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherApiServiceImpl implements WeatherApiService {
    private final WeatherApiClientService weatherApiClientService;

    @Override
    public FlightPossibilityResult evaluateFlightPossibility(Parameter parameters) {
        FlightPossibilityResult flightPossibilityResult = new FlightPossibilityResult();

        CurrentWeather currentWeather = weatherApiClientService.getCurrentWeather(parameters.getLocation());

        flightPossibilityResult.setCurrentWeather(currentWeather);

        List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();

        if (currentWeather.getCurrent().getTemperature() < parameters.getMinTemperature()) {
            inappropriateWeatherConditionsInfo.add("Минимально допустимая температура для полета = "
                    + parameters.getMinTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (ниже допустимой на "
                    + (parameters.getMinTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getTemperature() > parameters.getMaxTemperature()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая температура для полета = "
                    + parameters.getMaxTemperature()
                    + ". Текущая температура = " + currentWeather.getCurrent().getTemperature()
                    + " (выше допустимой на "
                    + (parameters.getMaxTemperature() - currentWeather.getCurrent().getTemperature()) + ")");
        }

        if (currentWeather.getCurrent().getWindSpeed() > parameters.getMaxWindSpeed()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая скорость ветра для полета = "
                    + parameters.getMaxWindSpeed()
                    + ". Текущая скорость ветра = " + currentWeather.getCurrent().getWindSpeed()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getWindSpeed() - parameters.getMaxWindSpeed()) + ")");
        }

        if (currentWeather.getCurrent().getWindGust() > parameters.getMaxWindGust()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимые порывы ветра для полета = "
                    + parameters.getMaxWindGust()
                    + ". Текущие порывы ветра = " + currentWeather.getCurrent().getWindGust()
                    + " (выше допустимых на "
                    + (currentWeather.getCurrent().getWindGust() - parameters.getMaxWindGust()) + ")");
        }

        if (currentWeather.getCurrent().getHumidity() > parameters.getMaxHumidity()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимая влажность для полета = "
                    + parameters.getMaxHumidity()
                    + ". Текущая влажность = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимой на "
                    + (currentWeather.getCurrent().getHumidity() - parameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPrecip() > parameters.getMaxPrecip()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое кол-во осадков для полета = "
                    + parameters.getMaxHumidity()
                    + ". Текущее кол-во осадков = " + currentWeather.getCurrent().getHumidity()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getHumidity() - parameters.getMaxHumidity()) + ")");
        }

        if (currentWeather.getCurrent().getPressure() > parameters.getMaxPressure()) {
            inappropriateWeatherConditionsInfo.add("Максимально допустимое давление для полета = "
                    + parameters.getMaxPressure()
                    + ". Текущее давление = " + currentWeather.getCurrent().getPressure()
                    + " (выше допустимого на "
                    + (currentWeather.getCurrent().getPressure() - parameters.getMaxPressure()) + ")");
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
}
