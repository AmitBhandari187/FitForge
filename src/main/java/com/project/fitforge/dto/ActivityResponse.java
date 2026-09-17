package com.project.fitforge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.fitforge.model.ActivityType;
import com.project.fitforge.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private Long activityId;
    private Long userId;
    private ActivityType type;
    private Map<String , Object> additionalMetrics;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
