package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations flightSimpleJdbcOperations;
    private final SimpleJdbcInsertOperations weatherSimpleJdbcOperations;

    public Parameter getParameterByUserId(Integer userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT user_id, language, location, min_temperature, max_temperature," +
                    " max_wind_speed, max_wind_gust, max_humidity, max_precip, max_pressure FROM parameter WHERE user_id = ?", new ParameterMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void saveFlightAndWeather(Flight flight) {

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
    }
}