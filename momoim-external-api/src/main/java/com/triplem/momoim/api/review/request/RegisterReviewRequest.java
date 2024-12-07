package com.triplem.momoim.api.review.request;

import com.triplem.momoim.core.domain.review.model.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RegisterReviewRequest {
    private Long gatheringId;
    private int score;
    private String title;
    private String comment;

    public Review toModel(Long userId) {
        return Review.builder()
            .userId(userId)
            .gatheringId(gatheringId)
            .score(score)
            .title(title)
            .comment(comment)
            .createdAt(LocalDateTime.now())
            .build();
    }
}
