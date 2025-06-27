package com.reprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercise_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "set_number")
    private Integer setNumber;

    private Integer reps;
    private Double weight;

    @Column(name = "rest_time_seconds")
    private Integer restTimeSeconds;

    @Column(name = "is_warmup")
    private boolean isWarmup = false;

    @Column(name = "is_failure")
    private boolean isFailure = false;

    @Column(length = 200)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    @JsonIgnore
    private WorkoutExercise workoutExercise;
}
