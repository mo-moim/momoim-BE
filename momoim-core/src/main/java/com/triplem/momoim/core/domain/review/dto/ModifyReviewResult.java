package com.triplem.momoim.core.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyReviewResult {
    private Long gatheringId;
    private int beforeScore;
    private int afterScore;
}
