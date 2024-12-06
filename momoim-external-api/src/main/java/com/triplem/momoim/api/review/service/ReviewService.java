package com.triplem.momoim.api.review.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.review.ModifyReview;
import com.triplem.momoim.core.domain.review.MyReview;
import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.ReviewDetail;
import com.triplem.momoim.core.domain.review.ReviewRegister;
import com.triplem.momoim.core.domain.review.ReviewRepository;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRegister reviewRegister;
    private final GatheringRepository gatheringRepository;

    public List<ReviewDetail> getReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return reviewRepository.getReviews(gatheringId, userId, paginationInformation);
    }

    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return reviewRepository.getMyReviews(userId, paginationInformation);
    }

    public List<GatheringPreview> getUnReviewGatherings(Long userId, PaginationInformation paginationInformation) {
        List<Long> unReviewGatheringIds = reviewRepository.getUnReviewGatheringIds(userId, paginationInformation);
        return gatheringRepository.getGatheringPreviews(unReviewGatheringIds);
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
            throw new BusinessException(ExceptionCode.FORBIDDEN_REVIEW);
        }

        reviewRepository.deleteById(reviewId);
    }
}
