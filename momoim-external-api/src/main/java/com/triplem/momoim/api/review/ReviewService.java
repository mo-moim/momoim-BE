package com.triplem.momoim.api.review;

import com.triplem.momoim.core.domain.review.ModifyReview;
import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.ReviewRegister;
import com.triplem.momoim.core.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRegister reviewRegister;

    public Review register(Review review) {
        return reviewRegister.review(review);
    }

    public void modify(ModifyReview modifyReview) {
        Review review = reviewRepository.findById(modifyReview.getReviewId());
        review.modify(modifyReview);
        reviewRepository.save(review);
    }
}