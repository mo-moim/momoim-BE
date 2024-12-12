package com.triplem.momoim.core.domain.review.infrastructure;

import static com.triplem.momoim.core.domain.gathering.infrastructure.QGatheringEntity.gatheringEntity;
import static com.triplem.momoim.core.domain.member.infrastructure.QGatheringMemberEntity.gatheringMemberEntity;
import static com.triplem.momoim.core.domain.review.infrastructure.QReviewEntity.reviewEntity;
import static com.triplem.momoim.core.domain.user.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.review.dto.MyReview;
import com.triplem.momoim.core.domain.review.dto.ReviewContent;
import com.triplem.momoim.core.domain.review.model.Review;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
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
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_REVIEW))
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
    public List<ReviewContent> getGatheringReviews(Long gatheringId, Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(
                Projections.constructor(
                    ReviewContent.class,
                    reviewEntity.id,
                    userEntity.id,
                    userEntity.name,
                    userEntity.profileImage,
                    reviewEntity.title,
                    reviewEntity.comment,
                    reviewEntity.score,
                    reviewEntity.createdAt
                )
            )
            .from(reviewEntity)
            .where(reviewEntity.gatheringId.eq(gatheringId))
            .leftJoin(userEntity).on(userEntity.id.eq(reviewEntity.userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(reviewEntity.id.desc())
            .fetch();
    }

    @Override
    public List<MyReview> getMyReviews(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(
                Projections.constructor(
                    MyReview.class,
                    reviewEntity.id,
                    reviewEntity.gatheringId,
                    reviewEntity.title,
                    reviewEntity.comment,
                    gatheringEntity.name,
                    gatheringEntity.status,
                    reviewEntity.score,
                    gatheringEntity.createdAt
                )
            )
            .from(reviewEntity)
            .where(reviewEntity.userId.eq(userId))
            .leftJoin(gatheringEntity).on(gatheringEntity.id.eq(reviewEntity.gatheringId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(reviewEntity.id.desc())
            .fetch();
    }

    @Override
    public List<Long> getUnReviewGatheringIds(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(gatheringEntity.id)
            .from(gatheringMemberEntity)
            .leftJoin(gatheringEntity).on(gatheringEntity.id.eq(gatheringMemberEntity.gatheringId))
            .leftJoin(reviewEntity).on(reviewEntity.gatheringId.eq(gatheringEntity.id))
            .where(gatheringMemberEntity.userId.eq(userId), reviewEntity.id.isNull())
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(gatheringEntity.id.desc())
            .fetch();
    }
}
