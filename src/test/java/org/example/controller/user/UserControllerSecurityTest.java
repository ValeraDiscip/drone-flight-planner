package org.example.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.security.SecurityConfig;
import org.example.controller.WithMockFlightPlannerUser;
import org.example.dto.request.RegisterUserRequest;
import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserResponse;
import org.example.entity.User;
import org.example.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class UserControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    public void anonymousUserRegisterTest() throws Exception {
        when(userService.saveUser(User.builder()
                .username(createRegisterUserRequest().getUsername())
                .password(createRegisterUserRequest().getPassword())
                .build()))
                .thenReturn(UserResponse.builder()
                        .build());

        mockMvc.perform(post("http://localhost:8080/user/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRegisterUserRequest())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void authorizedUserRegisterTest() throws Exception {
        mockMvc.perform(post("http://localhost:8080/user/register"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void anonymousUserUpdateTest() throws Exception {
        mockMvc.perform(post("http://localhost:8080/user/update"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockFlightPlannerUser
    public void authorizedUserUpdateTest() throws Exception {

        mockMvc.perform(post("http://localhost:8080/user/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserRequest())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private RegisterUserRequest createRegisterUserRequest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("user111");
        registerUserRequest.setPassword("11111");
        return registerUserRequest;
    }
}
