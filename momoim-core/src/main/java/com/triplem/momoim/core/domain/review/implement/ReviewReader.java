package com.triplem.momoim.core.domain.review.implement;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewContent;
import com.triplem.momoim.core.domain.review.dto.ReviewDetail;
import com.triplem.momoim.core.domain.review.infrastructure.ReviewRepository;
import com.triplem.momoim.core.domain.review.model.Review;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReader {
    private final ReviewRepository reviewRepository;

    public List<ReviewDetail> getGatheringReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        List<ReviewContent> reviews = reviewRepository.getGatheringReviews(gatheringId, userId, paginationInformation);
        return reviews
            .stream()
            .map(review -> new ReviewDetail(review, review.getWriterId().equals(userId)))
            .collect(Collectors.toList());
    }

    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return reviewRepository.getMyReviews(userId, paginationInformation);
    }

    public List<Long> getUnReviewGatheringIds(Long userId, PaginationInformation paginationInformation) {
        return reviewRepository.getUnReviewGatheringIds(userId, paginationInformation);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id);
    }
}
