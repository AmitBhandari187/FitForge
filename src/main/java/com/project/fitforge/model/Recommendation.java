package com.project.fitforge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId" , nullable = false,foreignKey = @ForeignKey(name = "fk_recommendation_user"))
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId" , nullable = false,foreignKey = @ForeignKey(name = "fk_recommendation_activity"))
    @JsonIgnore
    private Activity activity;
    @Column(length = 2000)
    private String recommendation;
    @JdbcTypeCode(SqlTypes.JSON)   // Using this because different activity can have different info
    @Column(columnDefinition = "json")
    private List<String> improvements;
    @JdbcTypeCode(SqlTypes.JSON)   // Using this because different activity can have different info
    @Column(columnDefinition = "json")
    private List<String> suggestions;
    @JdbcTypeCode(SqlTypes.JSON)   // Using this because different activity can have different info
    @Column(columnDefinition = "json")
    private List<String> safety;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
