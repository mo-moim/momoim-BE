package com.triplem.momoim.core.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ModifyReview {
    private Long reviewId;
    private Long userId;
    private int score;
    private String title;
    private String comment;
}
