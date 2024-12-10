package com.triplem.momoim.core.domain.review.implement;

import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import com.triplem.momoim.core.domain.review.dto.ModifyReviewResult;
import com.triplem.momoim.core.domain.review.infrastructure.ReviewRepository;
import com.triplem.momoim.core.domain.review.model.Review;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewUpdater {
    private final ReviewRepository reviewRepository;

    public ModifyReviewResult modifyReview(Long userId, ModifyReview modifyReview) {
        Review review = reviewRepository.findById(modifyReview.getReviewId());
        int beforeScore = review.getScore();

        if (!review.isWriter(userId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_REVIEW);
        }

        review.modify(modifyReview);
        reviewRepository.save(review);

        return new ModifyReviewResult(review.getGatheringId(), beforeScore, review.getScore());
    }
}
