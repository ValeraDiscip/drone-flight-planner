package org.example.dao;

import org.example.dto.client.Parameter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterMapper implements RowMapper<Parameter> {

    @Override
    public Parameter mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Parameter.builder()
                .userId(resultSet.getInt("user_id"))
                .language(resultSet.getString("language"))
                .location(resultSet.getString("location"))
                .minTemperature(resultSet.getDouble("min_temperature"))
                .maxTemperature(resultSet.getDouble("max_temperature"))
                .maxWindSpeed(resultSet.getDouble("max_wind_speed"))
                .maxWindGust(resultSet.getDouble("max_wind_gust"))
                .maxHumidity(resultSet.getDouble("max_humidity"))
                .maxPrecip(resultSet.getDouble("max_precip"))
                .maxPressure(resultSet.getDouble("max_pressure"))
                .build();
    }
}
