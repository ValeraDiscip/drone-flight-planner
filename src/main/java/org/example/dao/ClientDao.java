package org.example.dao;

import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;

public interface ClientDao {
    Parameter getParameterByClientId(Integer clientId);

    void insertWeather(Integer flightId, HourWeatherForecast weather);

    void insertFlight(Flight flight);
}
