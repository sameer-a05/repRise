package com.reprise.controller;

import com.reprise.entity.WorkoutExercise;
import com.reprise.service.WorkoutExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-exercises")
@RequiredArgsConstructor
public class WorkoutExerciseController {

    private final WorkoutExerciseService service;

    @GetMapping("/workout/{workoutId}")
    public List<WorkoutExercise> getByWorkout(@PathVariable Long workoutId) {
        return service.findByWorkoutIdWithSets(workoutId);
    }

    @PostMapping
    public WorkoutExercise create(@RequestBody WorkoutExercise we) {
        return service.save(we);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
