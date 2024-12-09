package com.triplem.momoim.core.domain.review.model;

import lombok.Getter;

@Getter
public class ReviewStatistic {
    private Long reviewId;
    private int oneScoreCount;
    private int twoScoreCount;
    private int threeScoreCount;
    private int fourScoreCount;
    private int fiveScoreCount;
    private int reviewCount;
    private int totalScore;
    private double averageScore;

    public ReviewStatistic(Long reviewId, int oneScoreCount, int twoScoreCount, int threeScoreCount, int fourScoreCount, int fiveScoreCount,
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
