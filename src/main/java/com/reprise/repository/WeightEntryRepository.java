package com.reprise.repository;

import com.reprise.entity.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {
    List<WeightEntry> findByUserIdOrderByEntryDateDesc(Long userId);

    @Query("SELECT we FROM WeightEntry we WHERE we.user.id = :userId AND we.entryDate BETWEEN :startDate AND :endDate ORDER BY we.entryDate DESC")
    List<WeightEntry> findByUserIdAndDateRange(@Param("userId") Long userId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    Optional<WeightEntry> findTopByUserIdOrderByEntryDateDesc(Long userId);

    @Query("SELECT AVG(we.weight) FROM WeightEntry we WHERE we.user.id = :userId AND we.entryDate BETWEEN :startDate AND :endDate")
    Double getAverageWeightInDateRange(@Param("userId") Long userId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);
}