package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.entity.ScheduledFlight;
import org.example.exception.UserAlreadyExistsException;
import org.example.entity.Flight;
import org.example.entity.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations flightSimpleJdbcOperations;
    private final SimpleJdbcInsertOperations weatherSimpleJdbcOperations;
    private final SimpleJdbcInsertOperations userSimpleJdbcOperations;
    private final SimpleJdbcInsertOperations scheduledFlightSimpleJdbcOperations;

    public Parameter getParameterByUserId(int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT id,    user_id, language, location, min_temperature, max_temperature," +
                    " max_wind_speed, max_wind_gust, max_humidity, max_precip, max_pressure FROM parameter WHERE user_id = ?", new ParameterMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public Flight saveFlightAndWeather(Flight flight) {

        MapSqlParameterSource flightParameters = new MapSqlParameterSource();
        flightParameters.addValue("user_id", flight.getUserId());
        flightParameters.addValue("time_of_flight", flight.getTimeOfFlight());
        flightParameters.addValue("successful", flight.getSuccessful());

        Number flightId = flightSimpleJdbcOperations.executeAndReturnKey(flightParameters);

        HourWeatherForecast weather = flight.getHourWeatherForecast();

        MapSqlParameterSource weatherParameters = new MapSqlParameterSource();
        weatherParameters.addValue("flight_id", flightId.intValue());
        weatherParameters.addValue("time", weather.getTime());
        weatherParameters.addValue("temperature", weather.getTemperature());
        weatherParameters.addValue("wind_speed", weather.getWindSpeed());
        weatherParameters.addValue("pressure", weather.getPressure());
        weatherParameters.addValue("humidity", weather.getHumidity());
        weatherParameters.addValue("precip", weather.getPrecip());
        weatherParameters.addValue("wind_gust", weather.getWindGust());

        weatherSimpleJdbcOperations.execute(weatherParameters);

        flight.setId(flightId.intValue());
        return flight;
    }

    @Override
    public User getUserByUsername(String username) {
        User foundUser;
        try {
            foundUser = jdbcTemplate.queryForObject("SELECT id, username, password, email FROM \"user\" WHERE username = ?", new UserMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (foundUser == null) {
            return null;
        }
        foundUser.setParameters(getParameterByUserId(foundUser.getId()));
        return foundUser;
    }

    @Override
    public User getUserById(int userId) {
        User foundUser;
        try {
            foundUser = jdbcTemplate.queryForObject("SELECT id, username, password, email FROM \"user\" WHERE id = ?", new UserMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (foundUser == null) {
            return null;
        }
        foundUser.setParameters(getParameterByUserId(foundUser.getId()));
        return foundUser;
    }

    @Override
    public User saveUser(User user) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", user.getUsername());
        parameters.addValue("password", user.getPassword());
        parameters.addValue("email", user.getEmail());
        Number userId;
        try {
            userId = userSimpleJdbcOperations.executeAndReturnKey(parameters);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        user.setId(userId.intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userForUpdate = getUserById(user.getId());

        if (userForUpdate == null) {
            return null;
        }
        if (user.getUsername() != null) {
            userForUpdate.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            userForUpdate.setPassword(user.getPassword());
        }
        if (user.getEmail() != null) {
            userForUpdate.setEmail(user.getEmail());
        }
        try {
            jdbcTemplate.update("UPDATE \"user\" SET username = ?, password = ?, email = ? WHERE id = ?",
                    userForUpdate.getUsername(), userForUpdate.getPassword(), userForUpdate.getEmail(), userForUpdate.getId());
        }
        catch (Exception e) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        return userForUpdate;
    }

    @Override
    public List<ScheduledFlight> getScheduledFlights() {
        return jdbcTemplate.query("SELECT id, user_id, time_of_flight, last_flight_possibility_decision" +
                " FROM scheduled_flight" +
                " WHERE time_of_flight > ?", new ScheduledFlightMapper(), LocalDateTime.now());
    }

    public ScheduledFlight saveScheduledFlight(ScheduledFlight scheduledFlight) {
        MapSqlParameterSource scheduledFlightParameters = new MapSqlParameterSource();
        scheduledFlightParameters.addValue("user_id", scheduledFlight.getUserId());
        scheduledFlightParameters.addValue("time_of_flight", scheduledFlight.getTimeOfFlight());
        scheduledFlightParameters.addValue("last_flight_possibility_decision", scheduledFlight.isLastFlightPossibilityDecision());

        Number scheduledFlightId = scheduledFlightSimpleJdbcOperations.executeAndReturnKey(scheduledFlightParameters);

        scheduledFlight.setFlightId(scheduledFlightId.intValue());
        return scheduledFlight;
    }

    @Override
    public void updateLastFlightPossibilityDecision(int userId, boolean lastFlightPossibilityDecision) {
        jdbcTemplate.update("UPDATE scheduled_flight SET last_flight_possibility_decision = ? WHERE user_id = ?", lastFlightPossibilityDecision, userId);
    }
}