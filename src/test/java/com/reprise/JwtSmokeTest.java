package com.reprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reprise.entity.User;
import com.reprise.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtSmokeTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;

    private String token;
    private User savedUser;

    @BeforeEach
    void setup() throws Exception {
        userRepository.deleteAll();

        // 1. Register user
        Map<String, Object> reg = new HashMap<>();
        reg.put("username", "testuser");
        reg.put("email", "test@example.com");
        reg.put("password", "password");
        reg.put("firstName", "Test");
        reg.put("lastName", "User");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isOk());

        savedUser = userRepository.findByUsername("testuser").orElseThrow();

        // 2. Log in and extract token
        Map<String, String> login = Map.of(
                "username", "testuser",
                "password", "password"
        );

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void testCreateWorkoutWithJwt() throws Exception {
        Map<String, Object> workout = new HashMap<>();
        workout.put("name", "Test Workout");
        workout.put("workDate", LocalDateTime.now().toString());
        workout.put("durationMinutes", 45);

        mockMvc.perform(post("/api/workouts/" + savedUser.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workout)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Workout"));
    }
}
