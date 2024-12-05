package com.triplem.momoim.core.domain.gathering;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
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
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.QGatheringMemberEntity;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
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
            .filter(gatheringEntity -> !gatheringEntity.getStatus().equals(GatheringStatus.CANCELED))
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
    public List<GatheringPreview> getGatheringPreviews(GatheringSearchOption searchOption) {
        return jpaQueryFactory.select(
                gatheringEntity,
                gatheringMemberEntity
            )
            .from(gatheringEntity)
            .where(whereGatheringSearchOption(searchOption), defaultGatheringFilter())
            .leftJoin(gatheringMemberEntity).on(gatheringMemberEntity.gatheringId.eq(gatheringEntity.id))
            .leftJoin(userEntity).on(userEntity.id.eq(gatheringMemberEntity.userId))
            .orderBy(sortGatheringSearch(searchOption.getSortType(), searchOption.getSortOrder()))
            .limit(searchOption.getPaginationInformation().getLimit())
            .offset(searchOption.getPaginationInformation().getOffset())
            .transform(
                groupBy(gatheringEntity.id)
                    .list(
                        Projections.constructor(
                            GatheringPreview.class,
                            gatheringEntity.id,
                            gatheringEntity.image,
                            gatheringEntity.name,
                            gatheringEntity.gatheringType,
                            gatheringEntity.status,
                            gatheringEntity.category,
                            gatheringEntity.subCategory,
                            gatheringEntity.location,
                            gatheringEntity.nextGatheringAt,
                            gatheringEntity.tags,
                            gatheringEntity.capacity,
                            gatheringEntity.participantCount,
                            gatheringEntity.isPeriodic,
                            list(
                                Projections.constructor(
                                    GatheringMemberDetail.class,
                                    gatheringMemberEntity.id,
                                    gatheringMemberEntity.userId,
                                    userEntity.email,
                                    userEntity.name,
                                    userEntity.profileImage,
                                    gatheringMemberEntity.joinedAt
                                ).skipNulls()
                            )
                        )
                    )
            );
    }

    @Override
    public List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        QGatheringMemberEntity members = new QGatheringMemberEntity("members");
        return jpaQueryFactory.select(
                gatheringEntity,
                gatheringMemberEntity
            )
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.userId.eq(userId), defaultGatheringFilter())
            .leftJoin(gatheringEntity).on(gatheringEntity.id.eq(gatheringMemberEntity.gatheringId))
            .leftJoin(members).on(members.gatheringId.eq(gatheringEntity.id))
            .leftJoin(userEntity).on(userEntity.id.eq(members.userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(gatheringEntity.id.desc())
            .transform(
                groupBy(gatheringEntity.id)
                    .list(Projections.constructor(
                            GatheringPreview.class,
                            gatheringEntity.id,
                            gatheringEntity.image,
                            gatheringEntity.name,
                            gatheringEntity.gatheringType,
                            gatheringEntity.status,
                            gatheringEntity.category,
                            gatheringEntity.subCategory,
                            gatheringEntity.location,
                            gatheringEntity.nextGatheringAt,
                            gatheringEntity.tags,
                            gatheringEntity.capacity,
                            gatheringEntity.participantCount,
                            gatheringEntity.isPeriodic,
                            list(
                                Projections.constructor(GatheringMemberDetail.class,
                                    members.id,
                                    members.userId,
                                    userEntity.email,
                                    userEntity.name,
                                    userEntity.profileImage,
                                    members.joinedAt
                                ).skipNulls()
                            )
                        )
                    )
            );
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
        return gatheringEntity.status.ne(GatheringStatus.CANCELED);
    }
}
