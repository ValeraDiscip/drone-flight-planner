package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.dto.client.Flight;
import org.example.dto.client.Parameter;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {
    private final JdbcTemplate jdbcTemplate;

    public Parameter getParameterByClientId(Integer clientId) {
        try {
            return jdbcTemplate.queryForObject("SELECT client_id, language, location, min_temperature, max_temperature," +
                    " max_wind_speed, max_wind_gust, max_humidity, max_precip, max_pressure FROM parameter WHERE client_id = ?", new ParameterMapper(), clientId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void saveFlight(Flight flight) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertFlightSql = "INSERT INTO flight (client_id, time_of_flight, successful) VALUES (?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertFlightSql, new String[]{"id"});
            ps.setInt(1, flight.getClientId());
            ps.setTimestamp(2, Timestamp.valueOf(flight.getTimeOfFlight()));
            ps.setBoolean(3, flight.getSuccessful());
            return ps;
        }, keyHolder);
        try {
            saveWeather(keyHolder.getKey().intValue(), flight.getHourWeatherForecast());
        } catch (NullPointerException e) {
            throw new RuntimeException("Ошибка при генерации id", e);
        }
    }

    public void saveWeather(Integer flightId, HourWeatherForecast weather) {
        jdbcTemplate.update("INSERT INTO weather " +
                        "(flight_id, time, temperature, wind_speed, pressure, humidity, precip, wind_gust)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                flightId, weather.getTime(), weather.getTemperature(), weather.getWindSpeed(),
                weather.getPressure(), weather.getHumidity(), weather.getPrecip(), weather.getWindGust());
    }
}

