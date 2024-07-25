package org.example;

import lombok.Data;
import org.example.dto.weather.current.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightResponse {
    private CurrentWeather currentWeather;
    private String conclusion;
    private List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();

    public void addInappropriateWeatherConditionInFo(String info) {
        inappropriateWeatherConditionsInfo.add(info);
    }
}
