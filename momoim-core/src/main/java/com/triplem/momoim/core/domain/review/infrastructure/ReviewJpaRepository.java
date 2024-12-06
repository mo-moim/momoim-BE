package com.triplem.momoim.core.domain.review.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
}
