package com.project.fitforge.repository;

import com.project.fitforge.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    List<Activity> findByUser_UserId(Long userId);
}
