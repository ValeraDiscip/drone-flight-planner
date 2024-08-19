package org.example.controller.flight;

import org.example.config.security.SecurityConfig;
import org.example.controller.WithMockFlightPlannerUser;
import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;
import org.example.service.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class FlightControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    @WithAnonymousUser
    public void anonymousUserEvaluateFlightPossibilityTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/flight/evaluateCurrentPossibility"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockFlightPlannerUser
    public void authorizedUserEvaluateFlightPossibilityTest() throws Exception {
        when(weatherService.evaluateCurrentFlightPossibility(1))
                .thenReturn(new FlightPossibilityResult());

        mockMvc.perform(get("http://localhost:8080/flight/evaluateCurrentPossibility"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void anonymousUserSaveFlightTest() throws Exception {
        mockMvc.perform(post("http://localhost:8080/flight/save"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockFlightPlannerUser
    public void authorizedUserSaveFlightTest() throws Exception {
        when(weatherService.saveFlightAndWeather(1, LocalDateTime.of(2024, 1, 1, 1,1), true))
                .thenReturn(FlightDto.builder().build());

        mockMvc.perform(post("http://localhost:8080/flight/save")
                        .param("timeOfFlight", "2024-01-01T01:01")
                        .param("successful", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
