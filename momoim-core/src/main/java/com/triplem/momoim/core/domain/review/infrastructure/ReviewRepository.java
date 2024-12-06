package com.triplem.momoim.core.domain.review.infrastructure;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewDetail;
import java.util.List;

public interface ReviewRepository {
    Review save(Review review);

    Review findById(Long id);

    Boolean isWrittenReview(Long userId, Long gatheringId);

    void deleteById(Long id);

    List<ReviewDetail> getReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation);

    List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation);

    List<Long> getUnReviewGatheringIds(Long userId, PaginationInformation paginationInformation);
}
