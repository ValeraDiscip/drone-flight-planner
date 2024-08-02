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
                        "pressure", "humidity", "precip", "wind_gust")
                .usingGeneratedKeyColumns("id");
    }
}
