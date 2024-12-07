package com.triplem.momoim.api.review.request;

import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import lombok.Getter;

@Getter
public class ModifyReviewRequest {
    private int score;
    private String title;
    private String comment;

    public ModifyReview toModel(Long reviewId) {
        return ModifyReview.builder()
            .reviewId(reviewId)
            .score(score)
            .title(title)
            .comment(comment)
            .build();
    }
}
