package com.triplem.momoim.core.domain.review;

import static com.triplem.momoim.core.domain.review.QReviewEntity.reviewEntity;
import static com.triplem.momoim.core.domain.user.QUserEntity.userEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    public List<ReviewDetail> getReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(
                reviewEntity.id,
                reviewEntity.userId,
                userEntity.name,
                userEntity.profileImage,
                reviewEntity.title,
                reviewEntity.comment,
                reviewEntity.score,
                reviewEntity.createdAt)
            .from(reviewEntity)
            .where(reviewEntity.gatheringId.eq(gatheringId))
            .leftJoin(userEntity).on(userEntity.id.eq(reviewEntity.userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(reviewEntity.id.desc())
            .fetch()
            .stream()
            .map(
                tuple -> ReviewDetail
                    .builder()
                    .reviewId(tuple.get(reviewEntity.id))
                    .isWriter(userId.equals(tuple.get(reviewEntity.userId)))
                    .writer(tuple.get(userEntity.name))
                    .writerProfileImage(tuple.get(userEntity.profileImage))
                    .title(tuple.get(reviewEntity.title))
                    .comment(tuple.get(reviewEntity.comment))
                    .createdAt(tuple.get(reviewEntity.createdAt))
                    .build()
            )
            .collect(Collectors.toList());
    }
}
