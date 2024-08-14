package org.example.dao;

import org.example.dto.ScheduledFlight;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduledFlightMapper implements RowMapper<ScheduledFlight> {
    @Override
    public ScheduledFlight mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ScheduledFlight.builder()
                .flightId(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .departureTime(resultSet.getTimestamp("departure_time").toLocalDateTime())
                .build();
    }
}
