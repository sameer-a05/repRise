package com.reprise.controller;

import com.reprise.entity.ExerciseSet;
import com.reprise.service.ExerciseSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sets")
@RequiredArgsConstructor
public class ExerciseSetController {

    private final ExerciseSetService service;

    @GetMapping("/workout-exercise/{workoutExerciseId}")
    public List<ExerciseSet> getByWorkoutExercise(@PathVariable Long workoutExerciseId) {
        return service.findByWorkoutExerciseId(workoutExerciseId);
    }

    @PostMapping
    public ExerciseSet create(@RequestBody ExerciseSet set) {
        return service.save(set);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
