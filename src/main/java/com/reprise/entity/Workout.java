package com.reprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String notes;

    @Column(name = "workout_date", nullable = false)
    private LocalDateTime workDate;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @CreationTimestamp
    @Column(name = "creation_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "workout" ,  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    public Double getTotalVolume(){
        return workoutExercises.stream()
                .flatMap(we -> we.getSets().stream())
                .mapToDouble(set -> (set.getWeight() != null? set.getWeight() : 0.0) *
                        (set.getReps() != null ? set.getReps() : 0))
                .sum();
    }


}
