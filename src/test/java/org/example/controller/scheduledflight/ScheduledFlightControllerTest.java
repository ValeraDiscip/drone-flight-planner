package org.example.controller.scheduledflight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.WithMockFlightPlannerUser;
import org.example.dto.FlightPossibilityResult;
import org.example.dto.request.SaveScheduledFlightRequest;
import org.example.service.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduledFlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherService weatherService;

    @Test
    @WithMockFlightPlannerUser
    public void saveScheduledFlightTest() throws Exception {
        FlightPossibilityResult flightPossibilityResult = new FlightPossibilityResult();
        flightPossibilityResult.setInappropriateWeatherConditionsInfo(new ArrayList<>());

        when(weatherService.evaluateFutureFlightPossibility(1, LocalDateTime.of(2222, 1, 1, 1, 1)))
                .thenReturn(flightPossibilityResult);

        SaveScheduledFlightRequest saveScheduledFlightRequest = new SaveScheduledFlightRequest();
        saveScheduledFlightRequest.setTimeOfFlight(LocalDateTime.of(2222, 1, 1, 1, 1));

        mockMvc.perform(post("http://localhost:8080/scheduledFlight/save").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveScheduledFlightRequest)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.timeOfFlight").value("2222-01-01 01:01"))
                .andExpect(jsonPath("$.lastFlightPossibilityDecision").value(true));
    }
}
