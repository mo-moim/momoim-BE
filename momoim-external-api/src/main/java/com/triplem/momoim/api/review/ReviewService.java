package com.triplem.momoim.api.review;

import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.ReviewRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRegister reviewRegister;

    public Review register(Review review) {
        return reviewRegister.review(review);
    }
}
