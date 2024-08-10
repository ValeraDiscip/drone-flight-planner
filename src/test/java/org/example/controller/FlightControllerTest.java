package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.weather.current.Current;
import org.example.dto.weather.current.CurrentWeather;
import org.example.dto.weather.forecast.DayWeatherForecast;
import org.example.dto.weather.forecast.Forecast;
import org.example.dto.weather.forecast.HourWeatherForecast;
import org.example.dto.weather.forecast.Weather;
import org.example.service.weather.WeatherApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherApiClient weatherApiClient;

    @Test
    @WithMockUser
    public void evaluateFlightPossibilityWithAppropriateWeatherTest() throws Exception {
        when(weatherApiClient.getCurrentWeather("Krasnodar"))
                .thenReturn(getCurrentWeather(true));

        mockMvc.perform(get("http://localhost:8080/flight/evaluateCurrentPossibility").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.conclusion").value("В настоящий момент полет является безопасным!"));

    }

    @Test
    @WithMockUser
    public void evaluateFlightPossibilityWithInappropriateWeatherTest() throws Exception {
        when(weatherApiClient.getCurrentWeather("Krasnodar"))
                .thenReturn(getCurrentWeather(false));

        mockMvc.perform(get("http://localhost:8080/flight/evaluateCurrentPossibility").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.inappropriateWeatherConditionsInfo").value(getInappropriateWeatherConditionsInfo()))
                .andExpect(jsonPath("$.conclusion").value("В настоящий момент полет не рекомендуется. " +
                        "Значения погодных условий не соответствуют выставленным параметрам."));
    }

    @Test
    @WithMockUser
    public void addNewFlightTest() throws Exception {
        when(weatherApiClient.getWeatherHistory("Krasnodar", "RU", 1, LocalDate.of(2020,1,1)))
                .thenReturn(getWeather());

        mockMvc.perform(post("http://localhost:8080/flight/add").param("userId", "1")
                .param("timeOfFlight", String.valueOf(LocalDateTime.of(2020,1,1,1,1)))
                .param("successful", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.successful").value("true"))
                .andExpect(jsonPath("$.timeOfFlight").value("2020-01-01 01:01"));
    }

    private Weather getWeather () {
        Weather weather = new Weather();
        Forecast forecast = new Forecast();
        DayWeatherForecast dayWeatherForecast = new DayWeatherForecast();
        HourWeatherForecast hourWeatherForecast = new HourWeatherForecast();
        hourWeatherForecast.setTime(LocalDateTime.of(2020,1,1,1,1));
        hourWeatherForecast.setHumidity(1.0);
        hourWeatherForecast.setWindSpeed(1.0);
        hourWeatherForecast.setPrecip(1.0);
        hourWeatherForecast.setTemperature(1.0);
        hourWeatherForecast.setPressure(1.0);
        hourWeatherForecast.setWindGust(1.0);
        dayWeatherForecast.setHourWeatherForecast(List.of(hourWeatherForecast));
        forecast.setDayWeatherForecast(List.of(dayWeatherForecast));
        weather.setForecast(forecast);
        return weather;
    }


    private CurrentWeather getCurrentWeather(Boolean appropriate) {
        CurrentWeather currentWeather = new CurrentWeather();
        Current current = new Current();
        if (appropriate) {
            current.setTemperature(25.0);
            current.setWindSpeed(18.0);
            current.setWindGust(18.0);
            current.setHumidity(50.0);
            current.setPrecip(0.1);
            current.setPressure(700.0);
            currentWeather.setCurrent(current);
        } else {
            current.setTemperature(35.0);
            current.setWindSpeed(21.0);
            current.setWindGust(30.0);
            current.setHumidity(99.0);
            current.setPrecip(2.0);
            current.setPressure(1100.0);
            currentWeather.setCurrent(current);
        }
        return currentWeather;
    }

    private List<String> getInappropriateWeatherConditionsInfo() {
        List<String> inappropriateWeatherConditionsInfo = new ArrayList<>();
        inappropriateWeatherConditionsInfo.add("Максимально допустимая температура для полета = 30.0. Текущая температура = 35.0 (выше допустимой на 5.0)");
        inappropriateWeatherConditionsInfo.add("Максимально допустимая скорость ветра для полета = 20.0. Текущая скорость ветра = 21.0 (выше допустимой на 1.0)");
        inappropriateWeatherConditionsInfo.add("Максимально допустимые порывы ветра для полета = 25.0. Текущие порывы ветра = 30.0 (выше допустимых на 5.0)");
        inappropriateWeatherConditionsInfo.add("Максимально допустимая влажность для полета = 80.0. Текущая влажность = 99.0 (выше допустимой на 19.0)");
        inappropriateWeatherConditionsInfo.add("Максимально допустимое кол-во осадков для полета = 80.0. Текущее кол-во осадков = 99.0 (выше допустимого на 19.0)");
        inappropriateWeatherConditionsInfo.add("Максимально допустимое давление для полета = 1000.0. Текущее давление = 1100.0 (выше допустимого на 100.0)");
        return inappropriateWeatherConditionsInfo;
    }
}
