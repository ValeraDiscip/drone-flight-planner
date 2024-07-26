package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.dto.client.Parameter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
