package com.reprise.repository;

import com.reprise.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    List<WorkoutExercise> findByWorkoutId(Long workoutId);
    List<WorkoutExercise> findByExerciseId(Long exerciseId);

    @Query("SELECT we FROM WorkoutExercise we LEFT JOIN FETCH we.sets WHERE we.workout.id = :workoutId ORDER BY we.exerciseOrder")
    List<WorkoutExercise> findByWorkoutIdWithSets(@Param("workoutId") Long workoutId);
}
