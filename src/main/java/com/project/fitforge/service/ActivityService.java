package com.project.fitforge.service;

import com.project.fitforge.dto.ActivityRequest;
import com.project.fitforge.dto.ActivityResponse;
import com.project.fitforge.model.Activity;
import com.project.fitforge.model.User;
import com.project.fitforge.repository.ActivityRepository;
import com.project.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        User user=userRepository.findById(activityRequest.getUserId()).orElseThrow(()->new RuntimeException("Invalid user :" + activityRequest.getUserId()));
        Activity activity=Activity.builder()
                .user(user)
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build();
        Activity savedActivity=activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse activityResponse=new ActivityResponse();
        activityResponse.setActivityId(savedActivity.getActivityId());
        activityResponse.setUserId(savedActivity.getUser().getUserId());
        activityResponse.setType(savedActivity.getType());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setStartTime(savedActivity.getStartTime());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setUpdatedAt(savedActivity.getUpdatedAt());

        return activityResponse;
    }
}
