package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;

import javax.sql.DataSource;

@Configuration
public class SimpleJdbcOperationsConfiguration {

    @Bean
    public SimpleJdbcInsertOperations flightSimpleJdbcOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("flight")
                .usingColumns("user_id", "time_of_flight", "successful")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsertOperations weatherSimpleJdbcOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("weather")
                .usingColumns("flight_id", "time", "temperature", "wind_speed",
                        "pressure", "humidity", "precip", "wind_gust");
    }

    @Bean
    public SimpleJdbcInsertOperations userSimpleJdbcOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("\"user\"")
                .usingColumns( "username", "password")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsertOperations scheduledFlightSimpleJdbcOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("scheduled_flight")
                .usingColumns("user_id", "time_of_flight", "last_flight_possibility_decision")
                .usingGeneratedKeyColumns("id");
    }
}
