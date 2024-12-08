package com.triplem.momoim.core.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDetail {
    private ReviewContent review;
    private Boolean isWriter;
}
