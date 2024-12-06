package com.triplem.momoim.core.domain.review;

import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;

public interface ReviewRepository {
    Review save(Review review);

    Review findById(Long id);

    Boolean isWrittenReview(Long userId, Long gatheringId);

    void deleteById(Long id);

    List<ReviewDetail> getReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation);

    List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation);
}
