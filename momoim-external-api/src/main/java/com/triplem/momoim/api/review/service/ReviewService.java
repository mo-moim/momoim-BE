package com.triplem.momoim.api.review.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.review.ModifyReview;
import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.ReviewDetail;
import com.triplem.momoim.core.domain.review.ReviewRegister;
import com.triplem.momoim.core.domain.review.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRegister reviewRegister;

    public List<ReviewDetail> getReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return reviewRepository.getReviews(gatheringId, userId, paginationInformation);
    }

    public Review register(Review review) {
        return reviewRegister.review(review);
    }

    public void modify(ModifyReview modifyReview) {
        Review review = reviewRepository.findById(modifyReview.getReviewId());
        review.modify(modifyReview);
        reviewRepository.save(review);
    }

    public void delete(Long requesterId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId);

        if (!review.isWriter(requesterId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }
}
