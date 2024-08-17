package org.example.dao;

import org.example.entity.ScheduledFlight;
import org.example.entity.Flight;
import org.example.entity.Parameter;
import org.example.entity.User;

import java.util.List;

public interface UserDao {
    Parameter getParameterByUserId(Integer userId);

    Flight saveFlightAndWeather(Flight flight);

    User getUserByUsername(String username);

    User saveUser(User user);

    List<ScheduledFlight> getScheduledFlights();

    ScheduledFlight saveScheduledFlight(ScheduledFlight scheduledFlight);

    String getEmailByUserId(Integer userId);

    void saveEmail(int userId, String email);

    void updateLastFlightPossibilityDecision(int userId, boolean lastFlightPossibilityDecision);
}
