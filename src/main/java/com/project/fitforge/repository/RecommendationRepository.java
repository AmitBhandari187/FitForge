package com.project.fitforge.repository;

import com.project.fitforge.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    List<Recommendation> findByUser_UserId(Long userId);

    List<Recommendation> findByActivity_ActivityId(Long activityId);
}
