package com.triplem.momoim.core.domain.review.implement;

import com.triplem.momoim.core.domain.review.infrastructure.ReviewStatisticRepository;
import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewStatisticUpdater {
    private final ReviewStatisticRepository reviewStatisticRepository;

    public void updateByNewReview(Long gatheringId, int score) {
        ReviewStatistic reviewStatistic = reviewStatisticRepository.findByGatheringId(gatheringId);
        reviewStatistic.updateByNewReview(score);
        reviewStatisticRepository.save(reviewStatistic);
    }

    public void updateByModifyReview(Long gatheringId, int beforeScore, int afterScore) {
        ReviewStatistic reviewStatistic = reviewStatisticRepository.findByGatheringId(gatheringId);
        reviewStatistic.updateByModifyReview(beforeScore, afterScore);
        reviewStatisticRepository.save(reviewStatistic);
    }
}
