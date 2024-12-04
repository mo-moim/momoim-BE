package com.triplem.momoim.core.domain.gathering;

import static com.triplem.momoim.core.domain.gathering.QGatheringEntity.gatheringEntity;
import static com.triplem.momoim.core.domain.member.QGatheringMemberEntity.gatheringMemberEntity;
import static com.triplem.momoim.core.domain.user.QUserEntity.userEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringRepositoryImpl implements GatheringRepository {
    private final GatheringJpaRepository gatheringJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Gathering save(Gathering gathering) {
        return gatheringJpaRepository.save(GatheringEntity.from(gathering))
            .toModel();
    }

    @Override
    public Gathering findById(Long id) {
        return gatheringJpaRepository.findById(id)
            .filter(gatheringEntity -> !gatheringEntity.getStatus().equals(GatheringStatus.DELETED))
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_GATHERING))
            .toModel();
    }

    @Override
    public GatheringDetail getGatheringDetail(Long gatheringId, Long userId) {
        GatheringDetail gatheringDetail = jpaQueryFactory.select(
                Projections.constructor(
                    GatheringDetail.class,
                    gatheringEntity.id,
                    gatheringEntity.managerId,
                    userEntity.name,
                    userEntity.profileImage,
                    gatheringEntity.category,
                    gatheringEntity.subCategory,
                    gatheringEntity.name,
                    gatheringEntity.gatheringType,
                    gatheringEntity.status,
                    gatheringEntity.image,
                    gatheringEntity.description,
                    gatheringEntity.address,
                    gatheringEntity.tags,
                    gatheringEntity.location,
                    gatheringEntity.capacity,
                    gatheringEntity.participantCount,
                    gatheringEntity.isPeriodic,
                    gatheringEntity.nextGatheringAt,
                    gatheringMemberEntity.id.isNotNull(),
                    Expressions.asBoolean(gatheringEntity.managerId.eq(userId))
                )
            )
            .from(gatheringEntity)
            .where(gatheringEntity.id.eq(gatheringId))
            .leftJoin(userEntity).on(userEntity.id.eq(gatheringEntity.managerId))
            .leftJoin(gatheringMemberEntity).on(gatheringMemberEntity.userId.eq(userId), gatheringMemberEntity.gatheringId.eq(gatheringId))
            .fetchFirst();

        if (gatheringDetail == null) {
            throw new BusinessException(ExceptionCode.NOT_FOUND_GATHERING);
        }

        return gatheringDetail;
    }

    @Override
    public List<Gathering> findBySearchOption(GatheringSearchOption searchOption) {
        return jpaQueryFactory.select(gatheringEntity)
            .from(gatheringEntity)
            .where(whereGatheringSearchOption(searchOption), defaultGatheringFilter())
            .orderBy(sortGatheringSearch(searchOption.getSortType(), searchOption.getSortOrder()))
            .limit(searchOption.getPaginationInformation().getLimit())
            .offset(searchOption.getPaginationInformation().getOffset())
            .fetch()
            .stream()
            .map(GatheringEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Gathering> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(gatheringEntity)
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.userId.eq(userId), defaultGatheringFilter())
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(gatheringEntity.id.desc())
            .innerJoin(gatheringEntity).on(gatheringEntity.id.eq(gatheringMemberEntity.gatheringId))
            .fetch()
            .stream()
            .map(GatheringEntity::toModel)
            .collect(Collectors.toList());
    }

    private BooleanBuilder whereGatheringSearchOption(GatheringSearchOption searchOption) {
        BooleanBuilder builder = new BooleanBuilder();

        if (searchOption.getCategory() != null) {
            builder.and(gatheringEntity.category.eq(searchOption.getCategory()));
        }

        if (searchOption.getSubCategory() != null) {
            builder.and(gatheringEntity.subCategory.eq(searchOption.getSubCategory()));
        }

        if (searchOption.getGatheringLocation() != null) {
            builder.and(gatheringEntity.location.eq(searchOption.getGatheringLocation()));
        }

        if (searchOption.getGatheringDate() != null) {
            builder.and(
                gatheringEntity.nextGatheringAt.between(
                    searchOption.getGatheringDate().atTime(0, 0, 0),
                    searchOption.getGatheringDate().atTime(23, 59, 59))
            );
        }

        return builder;
    }

    private OrderSpecifier<?> sortGatheringSearch(GatheringSortType sortType, SortOrder sortOrder) {
        switch (sortType) {
            case UPDATE_AT -> {
                return sortOrder.equals(SortOrder.ASC) ? gatheringEntity.lastModifiedAt.asc() : gatheringEntity.lastModifiedAt.desc();
            }
            case PARTICIPANT_COUNT -> {
                return sortOrder.equals(SortOrder.ASC) ? gatheringEntity.participantCount.asc() : gatheringEntity.participantCount.desc();
            }
            default -> {
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            }
        }
    }

    private BooleanExpression defaultGatheringFilter() {
        return gatheringEntity.status.ne(GatheringStatus.DELETED);
    }
}
