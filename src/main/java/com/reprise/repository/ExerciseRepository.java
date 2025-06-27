package com.reprise.repository;

import com.reprise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroup(String muscleGroup);
    List<Exercise> findByCategory(String category);
    List<Exercise> findByEquipmentNeeded(String equipmentNeeded);
    List<Exercise> findByIsCompound(Boolean isCompound);

    @Query("SELECT e FROM Exercise e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Exercise> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT e FROM Exercise e WHERE e.muscleGroup = :muscleGroup AND e.category = :category")
    List<Exercise> findByMuscleGroupAndCategory(@Param("muscleGroup") String muscleGroup,
                                                @Param("category") String category);
}
