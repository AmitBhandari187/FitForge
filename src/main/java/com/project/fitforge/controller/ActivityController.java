package com.project.fitforge.controller;

import com.project.fitforge.dto.ActivityRequest;
import com.project.fitforge.dto.ActivityResponse;
import com.project.fitforge.model.Activity;
import com.project.fitforge.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                activityService.getUserActivities(userId)
        );
    }
}
