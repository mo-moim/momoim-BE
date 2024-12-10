package com.triplem.momoim.core.domain.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewContent {
    private Long reviewId;
    private Long writerId;
    private String writer;
    private String writerProfileImage;
    private String title;
    private String comment;
    private int score;
    private LocalDateTime createdAt;
}
