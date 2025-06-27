package com.reprise.service;

import com.reprise.entity.Workout;
import com.reprise.entity.User;
import com.reprise.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public Workout createWorkout(Workout workout, User user) {
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    @Transactional(readOnly = true)
    public Optional<Workout> findById(Long id) {
        return workoutRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Workout findByIdWithDetails(Long id) {
        return workoutRepository.findByIdWithDetails(id);
    }

    @Transactional(readOnly = true)
    public List<Workout> findByUserId(Long userId) {
        return workoutRepository.findByUserIdOrderByWorkoutDateDesc(userId);
    }

    @Transactional(readOnly = true)
    public Page<Workout> findByUserId(Long userId, Pageable pageable) {
        return workoutRepository.findByUserIdOrderByWorkoutDateDesc(userId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Workout> findByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return workoutRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Workout> findByUserIdAndExerciseId(Long userId, Long exerciseId) {
        return workoutRepository.findByUserIdAndExerciseId(userId, exerciseId);
    }

    @Transactional(readOnly = true)
    public Long getWorkoutCountByUserId(Long userId) {
        return workoutRepository.countByUserId(userId);
    }

    public Workout updateWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Double getTotalVolumeForUser(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Workout> workouts = findByUserIdAndDateRange(userId, startDate, endDate);
        return workouts.stream()
                .mapToDouble(Workout::getTotalVolume)
                .sum();
    }

    @Transactional(readOnly = true)
    public List<Workout> getRecentWorkouts(Long userId, int limit) {
        return workoutRepository.findByUserIdOrderByWorkoutDateDesc(userId)
                .stream()
                .limit(limit)
                .toList();
    }
}
