package com.triplem.momoim.api.review;

import com.triplem.momoim.core.domain.review.ModifyReview;
import lombok.Getter;

@Getter
public class ModifyReviewRequest {
    private Long reviewId;
    private int score;
    private String title;
    private String comment;

    public ModifyReview toModel(Long userId) {
        return ModifyReview.builder()
            .reviewId(reviewId)
            .userId(userId)
            .score(score)
            .title(title)
            .comment(comment)
            .build();
    }
}
