package com.triplem.momoim.core.domain.review.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDetail {
    private Long reviewId;
    private Boolean isWriter;
    private String writer;
    private String writerProfileImage;
    private String title;
    private String comment;
    private int score;
    private LocalDateTime createdAt;
}