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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;

    private String token;
    private User savedUser;
    private Long createdWorkoutId;

    @BeforeEach
    void setup() throws Exception {
        userRepository.deleteAll();

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

        Map<String, String> login = Map.of("username", "testuser", "password", "password");

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void testCreateWorkout() throws Exception {
        Map<String, Object> workout = createWorkoutPayload();

        String response = mockMvc.perform(post("/api/workouts/" + savedUser.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workout)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push Day"))
                .andReturn().getResponse().getContentAsString();

        createdWorkoutId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    void testGetWorkoutsForUser() throws Exception {
        testCreateWorkout();

        mockMvc.perform(get("/api/workouts/user/" + savedUser.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Push Day")));
    }

    @Test
    void testDeleteWorkout() throws Exception {
        testCreateWorkout();

        mockMvc.perform(delete("/api/workouts/" + createdWorkoutId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/workouts/" + createdWorkoutId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetVolumeInDateRange() throws Exception {
        testCreateWorkout();

        String url = String.format("/api/workouts/volume/%d?start=%s&end=%s",
                savedUser.getId(),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));

        mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0")); // update if volume logic exists
    }

    private Map<String, Object> createWorkoutPayload() {
        Map<String, Object> workout = new HashMap<>();
        workout.put("name", "Push Day");
        workout.put("workDate", LocalDateTime.now().toString());
        workout.put("durationMinutes", 50);
        workout.put("notes", "Test workout");
        return workout;
    }
}
