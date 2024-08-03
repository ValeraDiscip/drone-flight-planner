package org.example.dao;

import org.example.entity.Flight;
import org.example.entity.Parameter;

public interface UserDao {
    Parameter getParameterByUserId(Integer userId);

    Flight saveFlightAndWeather(Flight flight);
}
