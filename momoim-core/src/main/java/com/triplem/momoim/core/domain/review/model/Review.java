package com.triplem.momoim.core.domain.review.model;

import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
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
    private String title;
    private String comment;
    private LocalDateTime createdAt;

    public void modify(ModifyReview modifyReview) {
        if (!isWriter(modifyReview.getUserId())) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_REVIEW);
        }

        this.score = modifyReview.getScore();
        this.title = modifyReview.getTitle();
        this.comment = modifyReview.getComment();
    }

    public Boolean isWriter(Long userId) {
        return this.userId.equals(userId);
    }
}
