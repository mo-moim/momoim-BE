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

    public void updateByNewReview(int score) {
        switch (score) {
            case 1 -> this.oneScoreCount++;
            case 2 -> this.twoScoreCount++;
            case 3 -> this.threeScoreCount++;
            case 4 -> this.fourScoreCount++;
            case 5 -> this.fiveScoreCount++;
        }

        this.totalScore += score;
        this.reviewCount += 1;
        refreshAverageScore();
    }

    public void updateByModifyReview(int beforeScore, int afterScore) {
        switch (beforeScore) {
            case 1 -> this.oneScoreCount--;
            case 2 -> this.twoScoreCount--;
            case 3 -> this.threeScoreCount--;
            case 4 -> this.fourScoreCount--;
            case 5 -> this.fiveScoreCount--;
        }

        switch (afterScore) {
            case 1 -> this.oneScoreCount++;
            case 2 -> this.twoScoreCount++;
            case 3 -> this.threeScoreCount++;
            case 4 -> this.fourScoreCount++;
            case 5 -> this.fiveScoreCount++;
        }

        this.totalScore += (afterScore - beforeScore);
        refreshAverageScore();
    }

    public void refreshAverageScore() {
        double value = (double) totalScore / (double) reviewCount;
        this.averageScore = Math.round(value * 10.0) / 10.0;
    }
}
