package org.example.controller.scheduledflight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.security.SecurityConfig;
import org.example.controller.WithMockFlightPlannerUser;
import org.example.dto.request.SaveScheduledFlightRequest;
import org.example.service.ScheduledFlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduledFlightController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class ScheduledFlightControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScheduledFlightService scheduledFlightService;

    @Test
    @WithAnonymousUser
    public void anonymousUserSaveScheduledFlightTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/scheduledFlight/save"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockFlightPlannerUser
    public void authorizedUserSaveScheduledFlightTest() throws Exception {
        SaveScheduledFlightRequest saveScheduledFlightRequest = new SaveScheduledFlightRequest();
        saveScheduledFlightRequest.setTimeOfFlight(LocalDateTime.now());

        mockMvc.perform(post("http://localhost:8080/scheduledFlight/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveScheduledFlightRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
