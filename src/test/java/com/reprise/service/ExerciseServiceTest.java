package com.reprise.service;

import com.reprise.entity.Exercise;
import com.reprise.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private Exercise testExercise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testExercise = new Exercise();
        testExercise.setId(1L);
        testExercise.setName("Bench Press");
        testExercise.setMuscleGroup("Chest");
        testExercise.setCategory("Compound");
    }

    @Test
    void createExercise_Success() {
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(testExercise);

        Exercise result = exerciseService.createExercise(testExercise);

        assertNotNull(result);
        assertEquals("Bench Press", result.getName());
        verify(exerciseRepository).save(testExercise);
    }

    @Test
    void findById_Success() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(testExercise));

        Optional<Exercise> result = exerciseService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Bench Press", result.get().getName());
    }

    @Test
    void findByMuscleGroup_ReturnsList() {
        List<Exercise> list = Arrays.asList(testExercise);
        when(exerciseRepository.findByMuscleGroup("CHEST")).thenReturn(list);

        List<Exercise> result = exerciseService.findByMuscleGroup("Chest");

        assertEquals(1, result.size());
        assertEquals("Bench Press", result.get(0).getName());
    }

    @Test
    void searchByName_ReturnsList() {
        List<Exercise> list = Arrays.asList(testExercise);
        when(exerciseRepository.findByNameContainingIgnoreCase("bench")).thenReturn(list);

        List<Exercise> result = exerciseService.searchByName("bench");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().toLowerCase().contains("bench"));
    }

    @Test
    void updateExercise_Success() {
        when(exerciseRepository.save(testExercise)).thenReturn(testExercise);

        Exercise result = exerciseService.updateExercise(testExercise);

        assertNotNull(result);
        verify(exerciseRepository).save(testExercise);
    }

    @Test
    void deleteExercise_Success() {
        doNothing().when(exerciseRepository).deleteById(1L);

        exerciseService.deleteExercise(1L);

        verify(exerciseRepository).deleteById(1L);
    }
}
