package com.triplem.momoim.core.domain.review.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review_statistic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewStatisticEntity {
    @Id
    private Long reviewId;

    @Column(nullable = false)
    private int oneScoreCount;

    @Column(nullable = false)
    private int twoScoreCount;

    @Column(nullable = false)
    private int threeScoreCount;

    @Column(nullable = false)
    private int fourScoreCount;

    @Column(nullable = false)
    private int fiveScoreCount;

    @Column(nullable = false)
    private int reviewCount;

    @Column(nullable = false)
    private int totalScore;

    @Column(nullable = false)
    private double averageScore;

    @Builder
    public ReviewStatisticEntity(Long reviewId, int oneScoreCount, int twoScoreCount, int threeScoreCount, int fourScoreCount, int fiveScoreCount,
        int reviewCount, int totalScore, double averageScore) {
        this.reviewId = reviewId;
        this.oneScoreCount = oneScoreCount;
        this.twoScoreCount = twoScoreCount;
        this.threeScoreCount = threeScoreCount;
        this.fourScoreCount = fourScoreCount;
        this.fiveScoreCount = fiveScoreCount;
        this.reviewCount = reviewCount;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }
}
