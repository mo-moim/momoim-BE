package com.triplem.momoim.api.review.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewDetail;
import com.triplem.momoim.core.domain.review.implement.ReviewReader;
import com.triplem.momoim.core.domain.review.implement.ReviewRegister;
import com.triplem.momoim.core.domain.review.implement.ReviewUpdater;
import com.triplem.momoim.core.domain.review.infrastructure.ReviewRepository;
import com.triplem.momoim.core.domain.review.model.Review;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewReader reviewReader;
    private final ReviewRepository reviewRepository;
    private final ReviewRegister reviewRegister;
    private final ReviewUpdater reviewUpdater;
    private final GatheringRepository gatheringRepository;

    public List<ReviewDetail> getGatheringReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getGatheringReviews(gatheringId, userId, paginationInformation);
    }

    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getMyReviews(userId, paginationInformation);
    }

    public List<GatheringPreview> getUnReviewGatherings(Long userId, PaginationInformation paginationInformation) {
        List<Long> unReviewGatheringIds = reviewRepository.getUnReviewGatheringIds(userId, paginationInformation);
        return gatheringRepository.getGatheringPreviews(unReviewGatheringIds);
    }

    public Review register(Review review) {
        return reviewRegister.review(review);
    }

    public void modify(Long userId, ModifyReview modifyReview) {
        reviewUpdater.modifyReview(userId, modifyReview);
    }

    public void delete(Long requesterId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId);

        if (!review.isWriter(requesterId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_REVIEW);
        }

        reviewRepository.deleteById(reviewId);
    }
}
