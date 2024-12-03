package com.triplem.momoim.core.domain.review;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Review {
    private Long id;
    private Long userId;
    private Long gatheringId;
    private int score;
    private String comment;
    private LocalDateTime createdAt;
}
