package com.triplem.momoim.core.domain.review.infrastructure;

import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
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
    private Long gatheringId;

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
    public ReviewStatisticEntity(Long gatheringId, int oneScoreCount, int twoScoreCount, int threeScoreCount, int fourScoreCount, int fiveScoreCount,
        int reviewCount, int totalScore, double averageScore) {
        this.gatheringId = gatheringId;
        this.oneScoreCount = oneScoreCount;
        this.twoScoreCount = twoScoreCount;
        this.threeScoreCount = threeScoreCount;
        this.fourScoreCount = fourScoreCount;
        this.fiveScoreCount = fiveScoreCount;
        this.reviewCount = reviewCount;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public static ReviewStatisticEntity from(ReviewStatistic reviewStatistic) {
        return ReviewStatisticEntity.builder()
            .gatheringId(reviewStatistic.getGatheringId())
            .oneScoreCount(reviewStatistic.getOneScoreCount())
            .twoScoreCount(reviewStatistic.getTwoScoreCount())
            .threeScoreCount(reviewStatistic.getThreeScoreCount())
            .fourScoreCount(reviewStatistic.getFourScoreCount())
            .fiveScoreCount(reviewStatistic.getFiveScoreCount())
            .reviewCount(reviewStatistic.getReviewCount())
            .totalScore(reviewStatistic.getTotalScore())
            .averageScore(reviewStatistic.getAverageScore())
            .build();
    }

    public ReviewStatistic toModel() {
        return ReviewStatistic.builder()
            .gatheringId(gatheringId)
            .oneScoreCount(oneScoreCount)
            .twoScoreCount(twoScoreCount)
            .threeScoreCount(threeScoreCount)
            .fourScoreCount(fourScoreCount)
            .fiveScoreCount(fiveScoreCount)
            .reviewCount(reviewCount)
            .totalScore(totalScore)
            .averageScore(averageScore)
            .build();
    }
}
