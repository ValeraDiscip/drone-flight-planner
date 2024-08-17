package org.example.dao;

import org.example.entity.ScheduledFlight;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduledFlightMapper implements RowMapper<ScheduledFlight> {
    @Override
    public ScheduledFlight mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ScheduledFlight.builder()
                .flightId(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .timeOfFlight(resultSet.getTimestamp("time_of_flight").toLocalDateTime())
                .lastFlightPossibilityDecision(resultSet.getBoolean("last_flight_possibility_decision"))
                .build();
    }
}
