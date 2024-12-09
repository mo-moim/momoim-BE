package com.triplem.momoim.api.review.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.review.dto.ModifyReview;
import com.triplem.momoim.core.domain.review.dto.ModifyReviewResult;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewDetail;
import com.triplem.momoim.core.domain.review.implement.ReviewReader;
import com.triplem.momoim.core.domain.review.implement.ReviewRegister;
import com.triplem.momoim.core.domain.review.implement.ReviewRemover;
import com.triplem.momoim.core.domain.review.implement.ReviewStatisticReader;
import com.triplem.momoim.core.domain.review.implement.ReviewStatisticUpdater;
import com.triplem.momoim.core.domain.review.implement.ReviewUpdater;
import com.triplem.momoim.core.domain.review.model.Review;
import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
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
    private final ReviewStatisticReader reviewStatisticReader;
    private final ReviewStatisticUpdater reviewStatisticUpdater;

    public List<ReviewDetail> getGatheringReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getGatheringReviews(gatheringId, userId, paginationInformation);
    }

    public ReviewStatistic getReviewStatistic(Long gatheringId) {
        return reviewStatisticReader.getReviewStatistic(gatheringId);
    }

    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return reviewReader.getMyReviews(userId, paginationInformation);
    }

    public List<GatheringPreview> getUnReviewGatherings(Long userId, PaginationInformation paginationInformation) {
        List<Long> unReviewGatheringIds = reviewReader.getUnReviewGatheringIds(userId, paginationInformation);
        return gatheringReader.getGatheringPreviewsById(unReviewGatheringIds);
    }

    public Review register(Review review) {
        Review savedReview = reviewRegister.review(review);
        reviewStatisticUpdater.updateByNewReview(review.getGatheringId(), review.getScore());
        return savedReview;
    }

    public void modify(Long userId, ModifyReview modifyReview) {
        ModifyReviewResult result = reviewUpdater.modifyReview(userId, modifyReview);
        reviewStatisticUpdater.updateByModifyReview(result.getGatheringId(), result.getBeforeScore(), result.getAfterScore());
    }

    public void delete(Long userId, Long reviewId) {
        reviewRemover.deleteReview(userId, reviewId);
    }
}
