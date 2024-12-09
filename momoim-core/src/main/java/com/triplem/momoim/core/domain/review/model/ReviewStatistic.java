package com.triplem.momoim.core.domain.review.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewStatistic {
    private Long gatheringId;
    private int oneScoreCount;
    private int twoScoreCount;
    private int threeScoreCount;
    private int fourScoreCount;
    private int fiveScoreCount;
    private int reviewCount;
    private int totalScore;
    private double averageScore;

    @Builder
    public ReviewStatistic(Long gatheringId, int oneScoreCount, int twoScoreCount, int threeScoreCount, int fourScoreCount, int fiveScoreCount,
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

    public static ReviewStatistic create(Long gatheringId) {
        return ReviewStatistic
            .builder()
            .gatheringId(gatheringId)
            .oneScoreCount(0)
            .twoScoreCount(0)
            .threeScoreCount(0)
            .fourScoreCount(0)
            .fiveScoreCount(0)
            .reviewCount(0)
            .totalScore(0)
            .averageScore(0.0)
            .build();
    }
}
