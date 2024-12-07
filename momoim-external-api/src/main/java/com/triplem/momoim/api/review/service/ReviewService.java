package com.triplem.momoim.api.review.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewDetail;
import com.triplem.momoim.core.domain.review.implement.ReviewReader;
import com.triplem.momoim.core.domain.review.implement.ReviewRegister;
import com.triplem.momoim.core.domain.review.implement.ReviewRemover;
import com.triplem.momoim.core.domain.review.implement.ReviewUpdater;
import com.triplem.momoim.core.domain.review.model.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewReader reviewReader;
    private final ReviewRegister reviewRegister;
    private final ReviewUpdater reviewUpdater;
    private final ReviewRemover reviewRemover;
    private final GatheringReader gatheringReader;

    public List<ReviewDetail> getGatheringReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getGatheringReviews(gatheringId, userId, paginationInformation);
    }

    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getMyReviews(userId, paginationInformation);
    }

    public List<GatheringPreview> getUnReviewGatherings(Long userId, PaginationInformation paginationInformation) {
        List<Long> unReviewGatheringIds = reviewReader.getUnReviewGatheringIds(userId, paginationInformation);
        return gatheringReader.getGatheringPreviewsById(unReviewGatheringIds);
    }

    public Review register(Review review) {
        return reviewRegister.review(review);
    }

    public void modify(Long userId, ModifyReview modifyReview) {
        reviewUpdater.modifyReview(userId, modifyReview);
    }

    public void delete(Long userId, Long reviewId) {
        reviewRemover.deleteReview(userId, reviewId);
    }
}
