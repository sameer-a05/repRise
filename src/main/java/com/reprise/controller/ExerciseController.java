package com.reprise.controller;

import com.reprise.entity.Exercise;
import com.reprise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<Exercise> getAll() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getById(@PathVariable Long id) {
        return exerciseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Exercise create(@RequestBody Exercise exercise) {
        return exerciseService.createExercise(exercise);
    }

    @PutMapping
    public Exercise update(@RequestBody Exercise exercise) {
        return exerciseService.updateExercise(exercise);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }
}
