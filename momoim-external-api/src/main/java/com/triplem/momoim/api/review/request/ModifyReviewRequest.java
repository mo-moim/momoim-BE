package com.triplem.momoim.api.review.request;

import com.triplem.momoim.core.domain.review.ModifyReview;
import lombok.Getter;

@Getter
public class ModifyReviewRequest {
    private int score;
    private String title;
    private String comment;

    public ModifyReview toModel(Long reviewId, Long userId) {
        return ModifyReview.builder()
            .reviewId(reviewId)
            .userId(userId)
            .score(score)
            .title(title)
            .comment(comment)
            .build();
    }
}
