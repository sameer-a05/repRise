package com.reprise.repository;


import com.reprise.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    List<Workout> findByUserIdOrderByWorkoutDateDesc(Long userId);
    Page<Workout> findByUserIdOrderByWorkoutDateDesc(Long userId, Pageable pageable);

    @Query("SELECT w FROM Workout w LEFT JOIN FETCH w.workoutExercises we LEFT JOIN FETCH we.sets WHERE w.id = :workoutId")
    Workout findByIdWithDetails(@Param("workoutId") Long workoutId);

    @Query("SELECT w FROM Workout w WHERE w.user.id = :userId AND w.workoutDate BETWEEN :startDate AND :endDate ORDER BY w.workoutDate DESC")
    List<Workout> findByUserIdAndDateRange(@Param("userId") Long userId,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(w) FROM Workout w WHERE w.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Workout w JOIN w.workoutExercises we WHERE w.user.id = :userId AND we.exercise.id = :exerciseId ORDER BY w.workoutDate DESC")
    List<Workout> findByUserIdAndExerciseId(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);
}
