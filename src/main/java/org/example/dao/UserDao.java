package org.example.dao;

import org.example.dto.ScheduledFlight;
import org.example.entity.Flight;
import org.example.entity.Parameter;
import org.example.entity.User;

public interface UserDao {
    Parameter getParameterByUserId(Integer userId);

    Flight saveFlightAndWeather(Flight flight);

    User getUserByUsername(String username);

    User saveUser(User user);

    ScheduledFlight getScheduledFlightByUserId(Integer userId);
}
