package com.reprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "muscle_group")
    private String muscleGroup;

    @Column(name = "equipment_needed")
    private String equipmentNeeded;

    private String category;

    @Column(name = "is_compound")
    private Boolean isCompound = false;

    @OneToMany(mappedBy = "exercise" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();
}
