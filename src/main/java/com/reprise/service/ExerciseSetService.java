package com.reprise.service;

import com.reprise.entity.ExerciseSet;
import com.reprise.repository.ExerciseSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;

    public ExerciseSet save(ExerciseSet set) {
        return exerciseSetRepository.save(set);
    }

    @Transactional(readOnly = true)
    public Optional<ExerciseSet> findById(Long id) {
        return exerciseSetRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ExerciseSet> findByWorkoutExerciseId(Long workoutExerciseId) {
        return exerciseSetRepository.findByWorkoutExerciseIdOrderBySetNumber(workoutExerciseId);
    }

    @Transactional(readOnly = true)
    public List<ExerciseSet> findByUserIdAndExerciseId(Long userId, Long exerciseId) {
        return exerciseSetRepository.findByUserIdAndExerciseId(userId, exerciseId);
    }

    @Transactional(readOnly = true)
    public List<ExerciseSet> findPRsByUserAndExercise(Long userId, Long exerciseId) {
        return exerciseSetRepository.findPersonalRecordsByUserIdAndExerciseId(userId, exerciseId);
    }

    public void delete(Long id) {
        exerciseSetRepository.deleteById(id);
    }
}
