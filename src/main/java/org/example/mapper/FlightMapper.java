package org.example.mapper;

import org.example.dto.FlightDto;
import org.example.entity.Flight;

public class FlightMapper {
    public static FlightDto flightToFlightDto(Flight flight) {
        return FlightDto.builder()
                .id(flight.getId())
                .timeOfFlight(flight.getTimeOfFlight())
                .successful(flight.getSuccessful())
                .hourWeatherForecast(flight.getHourWeatherForecast())
                .build();
    }
}
