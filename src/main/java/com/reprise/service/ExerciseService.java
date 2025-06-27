package com.reprise.service;

import com.reprise.entity.Exercise;
import com.reprise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Transactional(readOnly = true)
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Exercise> findByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup.toUpperCase());
    }

    @Transactional(readOnly = true)
    public List<Exercise> findByCategory(String category) {
        return exerciseRepository.findByCategory(category.toUpperCase());
    }

    @Transactional(readOnly = true)
    public List<Exercise> searchByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Exercise> findByMuscleGroupAndCategory(String muscleGroup, String category) {
        return exerciseRepository.findByMuscleGroupAndCategory(
                muscleGroup.toUpperCase(),
                category.toUpperCase()
        );
    }

    public Exercise updateExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
