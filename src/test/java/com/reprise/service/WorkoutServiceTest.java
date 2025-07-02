package com.reprise.service;

import com.reprise.entity.User;
import com.reprise.entity.Workout;
import com.reprise.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private User testUser;
    private Workout testWorkout;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testWorkout = new Workout();
        testWorkout.setId(1L);
        testWorkout.setName("Test Workout");
        testWorkout.setWorkDate(LocalDateTime.now());
        testWorkout.setUser(testUser);
    }

    @Test
    void createWorkout_Success() {
        when(workoutRepository.save(any(Workout.class))).thenReturn(testWorkout);

        Workout result = workoutService.createWorkout(testWorkout, testUser);

        assertNotNull(result);
        assertEquals("Test Workout", result.getName());
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void findById_Success() {
        when(workoutRepository.findById(1L)).thenReturn(Optional.of(testWorkout));

        Optional<Workout> result = workoutService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Workout", result.get().getName());
    }

    @Test
    void deleteWorkout_Success() {
        doNothing().when(workoutRepository).deleteById(1L);

        workoutService.deleteWorkout(1L);

        verify(workoutRepository).deleteById(1L);
    }
}
