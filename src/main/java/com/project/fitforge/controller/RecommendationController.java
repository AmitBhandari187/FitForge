package com.project.fitforge.controller;

import com.project.fitforge.dto.RecommendationRequest;
import com.project.fitforge.model.Activity;
import com.project.fitforge.model.Recommendation;
import com.project.fitforge.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendations(@RequestBody RecommendationRequest recommendationRequest){
        return ResponseEntity.ok(recommendationService.generateRecommendations(recommendationRequest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable Long userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendations(@PathVariable Long activityId){
        return ResponseEntity.ok(recommendationService.getActivityRecommendations(activityId));
    }
}
