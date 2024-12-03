package com.triplem.momoim.core.domain.review;

public interface ReviewRepository {
    Review save(Review review);

    Review findById(Long id);

    Boolean isWrittenReview(Long userId, Long gatheringId);

    void deleteById(Long id);
}
