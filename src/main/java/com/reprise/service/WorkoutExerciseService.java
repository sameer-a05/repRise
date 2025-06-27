package com.reprise.service;

import com.reprise.entity.WorkoutExercise;
import com.reprise.repository.WorkoutExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExercise save(WorkoutExercise workoutExercise) {
        return workoutExerciseRepository.save(workoutExercise);
    }

    @Transactional(readOnly = true)
    public Optional<WorkoutExercise> findById(Long id) {
        return workoutExerciseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<WorkoutExercise> findByWorkoutId(Long workoutId) {
        return workoutExerciseRepository.findByWorkoutId(workoutId);
    }

    @Transactional(readOnly = true)
    public List<WorkoutExercise> findByWorkoutIdWithSets(Long workoutId) {
        return workoutExerciseRepository.findByWorkoutIdWithSets(workoutId);
    }

    public void delete(Long id) {
        workoutExerciseRepository.deleteById(id);
    }
}
