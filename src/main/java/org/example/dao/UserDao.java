package org.example.dao;

import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;

public interface UserDao {
    Parameter getParameterByUserId(Integer userId);

    void saveFlightAndWeather(Flight flight);
}
