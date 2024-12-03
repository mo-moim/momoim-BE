package com.triplem.momoim.core.domain.review;

import static com.triplem.momoim.core.domain.review.QReviewEntity.reviewEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(ReviewEntity.from(review)).toModel();
    }

    @Override
    public Review findById(Long id) {
        return reviewJpaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."))
            .toModel();
    }

    @Override
    public Boolean isWrittenReview(Long userId, Long gatheringId) {
        return jpaQueryFactory.select(reviewEntity.id)
            .from(reviewEntity)
            .where(reviewEntity.userId.eq(userId), reviewEntity.gatheringId.eq(gatheringId))
            .fetchFirst() != null;
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }
}
