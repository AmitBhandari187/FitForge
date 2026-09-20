package com.project.fitforge.service;

import com.project.fitforge.dto.RecommendationRequest;
import com.project.fitforge.model.Activity;
import com.project.fitforge.model.Recommendation;
import com.project.fitforge.model.User;
import com.project.fitforge.repository.ActivityRepository;
import com.project.fitforge.repository.RecommendationRepository;
import com.project.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public Recommendation generateRecommendations(RecommendationRequest recommendationRequest) {
        User user=userRepository.findById(recommendationRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not found :"+ recommendationRequest.getUserId()));
        Activity activity=activityRepository.findById(recommendationRequest.getActivityId()).orElseThrow(()-> new RuntimeException("User not found :"+ recommendationRequest.getActivityId()));

        Recommendation recommendation=Recommendation.builder()
                .user(user)
                .activity(activity)
                .safety(recommendationRequest.getSafety())
                .recommendation(recommendationRequest.getRecommendation())
                .suggestions(recommendationRequest.getSuggestions())
                .improvements(recommendationRequest.getImprovements())
                .build();

        return recommendationRepository.save(recommendation);

    }

    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationRepository.findByUser_UserId(userId);
    }

    public List<Recommendation> getActivityRecommendations(Long activityId) {
        return recommendationRepository.findByActivity_ActivityId(activityId);
    }
}
