package com.reprise.controller;

import com.reprise.entity.User;
import com.reprise.entity.WeightEntry;
import com.reprise.service.UserService;
import com.reprise.service.WeightEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weights")
@RequiredArgsConstructor
public class WeightEntryController {

    private final WeightEntryService service;
    private final UserService userService;

    @PostMapping("/{userId}")
    public WeightEntry create(@PathVariable Long userId, @RequestBody WeightEntry entry) {
        User user = userService.findById(userId).orElseThrow();
        return service.createWeightEntry(entry, user);
    }

    @GetMapping("/user/{userId}")
    public List<WeightEntry> getByUser(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/change/{userId}")
    public Double getChange(@PathVariable Long userId, @RequestParam int days) {
        return service.getWeightChange(userId, days);
    }
}
