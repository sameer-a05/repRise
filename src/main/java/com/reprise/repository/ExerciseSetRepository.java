package com.reprise.repository;

import com.reprise.entity.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findByWorkoutExerciseIdOrderBySetNumber(Long workoutExerciseId);

    @Query("SELECT es FROM ExerciseSet es JOIN es.workoutExercise we JOIN we.workout w WHERE w.user.id = :userId AND we.exercise.id = :exerciseId ORDER BY w.workoutDate DESC, es.setNumber")
    List<ExerciseSet> findByUserIdAndExerciseId(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);

    @Query("SELECT es FROM ExerciseSet es JOIN es.workoutExercise we JOIN we.workout w WHERE w.user.id = :userId AND we.exercise.id = :exerciseId AND es.weight = (SELECT MAX(es2.weight) FROM ExerciseSet es2 JOIN es2.workoutExercise we2 JOIN we2.workout w2 WHERE w2.user.id = :userId AND we2.exercise.id = :exerciseId)")
    List<ExerciseSet> findPersonalRecordsByUserIdAndExerciseId(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);
}