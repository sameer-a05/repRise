package com.reprise.controller;

import com.reprise.entity.Workout;
import com.reprise.entity.User;
import com.reprise.service.UserService;
import com.reprise.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<Workout> create(@PathVariable Long userId, @RequestBody Workout workout) {
        User user = userService.findById(userId).orElseThrow();
        return ResponseEntity.ok(workoutService.createWorkout(workout, user));
    }

    @GetMapping("/user/{userId}")
    public List<Workout> getByUser(@PathVariable Long userId) {
        return workoutService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getById(@PathVariable Long id) {
        return workoutService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
    }

    @GetMapping("/volume/{userId}")
    public Double getVolume(@PathVariable Long userId,
                            @RequestParam LocalDateTime start,
                            @RequestParam LocalDateTime end) {
        return workoutService.getTotalVolumeForUser(userId, start, end);
    }
}
