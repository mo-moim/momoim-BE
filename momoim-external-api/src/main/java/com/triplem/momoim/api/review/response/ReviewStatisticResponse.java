package com.triplem.momoim.api.review.response;

import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewStatisticResponse {
    private int oneScoreCount;
    private int twoScoreCount;
    private int threeScoreCount;
    private int fourScoreCount;
    private int fiveScoreCount;
    private double averageScore;

    public static ReviewStatisticResponse from(ReviewStatistic reviewStatistic) {
        return new ReviewStatisticResponse(
            reviewStatistic.getOneScoreCount(),
            reviewStatistic.getTwoScoreCount(),
            reviewStatistic.getThreeScoreCount(),
            reviewStatistic.getFourScoreCount(),
            reviewStatistic.getFiveScoreCount(),
            reviewStatistic.getAverageScore()
        );
    }
}
