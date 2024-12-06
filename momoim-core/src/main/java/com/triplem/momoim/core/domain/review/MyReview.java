package com.triplem.momoim.core.domain.review;

import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyReview {
    private Long reviewId;
    private Long gatheringId;
    private String title;
    private String comment;
    private String gatheringName;
    private GatheringStatus gatheringStatus;
    private int score;
    private LocalDateTime createdAt;
}
