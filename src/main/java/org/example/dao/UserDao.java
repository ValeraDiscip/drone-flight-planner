package org.example.dao;

import org.example.entity.ScheduledFlight;
import org.example.entity.Flight;
import org.example.entity.Parameter;
import org.example.entity.User;

import java.util.List;

public interface UserDao {
    Parameter getParameterByUserId(int userId);

    Flight saveFlightAndWeather(Flight flight);

    User getUserByUsername(String username);

    User getUserById(int userId);

    User saveUser(User user);

    User updateUser(User user);

    List<ScheduledFlight> getScheduledFlights();

    ScheduledFlight saveScheduledFlight(ScheduledFlight scheduledFlight);

    void updateLastFlightPossibilityDecision(int userId, boolean lastFlightPossibilityDecision);
}
