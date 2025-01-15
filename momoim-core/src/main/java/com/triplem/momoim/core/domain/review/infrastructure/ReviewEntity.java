package com.triplem.momoim.core.domain.review.infrastructure;

import com.triplem.momoim.core.domain.review.model.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long gatheringId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static ReviewEntity from(Review review) {
        return ReviewEntity.builder()
            .id(review.getId())
            .userId(review.getUserId())
            .gatheringId(review.getGatheringId())
            .score(review.getScore())
            .title(review.getTitle())
            .comment(review.getComment())
            .createdAt(review.getCreatedAt())
            .build();
    }

    public Review toModel() {
        return Review.builder()
            .id(id)
            .userId(userId)
            .gatheringId(gatheringId)
            .score(score)
            .title(title)
            .comment(comment)
            .createdAt(createdAt)
            .build();
    }
}
