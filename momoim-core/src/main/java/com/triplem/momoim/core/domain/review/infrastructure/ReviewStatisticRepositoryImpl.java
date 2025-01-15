package com.triplem.momoim.core.domain.review.infrastructure;

import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewStatisticRepositoryImpl implements ReviewStatisticRepository {
    private final ReviewStatisticJpaRepository reviewStatisticJpaRepository;

    @Override
    public ReviewStatistic save(ReviewStatistic reviewStatistic) {
        return reviewStatisticJpaRepository.save(ReviewStatisticEntity.from(reviewStatistic)).toModel();
    }

    @Override
    public ReviewStatistic findByGatheringId(Long gatheringId) {
        return reviewStatisticJpaRepository.findById(gatheringId)
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_GATHERING))
            .toModel();
    }
}
