package org.example.dao;

import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;

public interface ClientDao {
    Parameter getParameterByClientId(Integer clientId);

    void saveFlightAndWeather(Flight flight);
}
