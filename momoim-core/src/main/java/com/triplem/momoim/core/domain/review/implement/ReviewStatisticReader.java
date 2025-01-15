package com.triplem.momoim.core.domain.review.implement;

import com.triplem.momoim.core.domain.review.infrastructure.ReviewStatisticRepository;
import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewStatisticReader {
    private final ReviewStatisticRepository reviewStatisticRepository;

    public ReviewStatistic getReviewStatistic(Long gatheringId) {
        return reviewStatisticRepository.findByGatheringId(gatheringId);
    }
}
