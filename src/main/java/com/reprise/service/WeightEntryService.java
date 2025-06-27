package com.reprise.service;

import com.reprise.entity.WeightEntry;
import com.reprise.entity.User;
import com.reprise.repository.WeightEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WeightEntryService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntry createWeightEntry(WeightEntry weightEntry, User user) {
        weightEntry.setUser(user);
        return weightEntryRepository.save(weightEntry);
    }

    @Transactional(readOnly = true)
    public List<WeightEntry> findByUserId(Long userId) {
        return weightEntryRepository.findByUserIdOrderByEntryDateDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<WeightEntry> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return weightEntryRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Optional<WeightEntry> getLatestWeightEntry(Long userId) {
        return weightEntryRepository.findTopByUserIdOrderByEntryDateDesc(userId);
    }

    @Transactional(readOnly = true)
    public Double getAverageWeight(Long userId, LocalDate startDate, LocalDate endDate) {
        return weightEntryRepository.getAverageWeightInDateRange(userId, startDate, endDate);
    }

    public WeightEntry updateWeightEntry(WeightEntry weightEntry) {
        return weightEntryRepository.save(weightEntry);
    }

    public void deleteWeightEntry(Long id) {
        weightEntryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Double getWeightChange(Long userId, int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);

        List<WeightEntry> entries = findByUserIdAndDateRange(userId, startDate, endDate);
        if (entries.size() < 2) return 0.0;

        WeightEntry latest = entries.get(0);
        WeightEntry oldest = entries.get(entries.size() - 1);
        return latest.getWeight() - oldest.getWeight();
    }
}
