package com.triplem.momoim.core.domain.review.implement;

import com.triplem.momoim.core.domain.review.infrastructure.ReviewRepository;
import com.triplem.momoim.core.domain.review.model.Review;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewRemover {
    private final ReviewRepository reviewRepository;

    public void deleteReview(Long userId, Review review) {
        if (!review.isWriter(userId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_REVIEW);
        }

        reviewRepository.deleteById(review.getId());
    }
}
