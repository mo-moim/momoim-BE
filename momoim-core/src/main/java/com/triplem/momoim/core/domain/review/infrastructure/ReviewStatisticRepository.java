package com.triplem.momoim.core.domain.review.infrastructure;

import com.triplem.momoim.core.domain.review.model.ReviewStatistic;

public interface ReviewStatisticRepository {
    ReviewStatistic save(ReviewStatistic reviewStatistic);
}
