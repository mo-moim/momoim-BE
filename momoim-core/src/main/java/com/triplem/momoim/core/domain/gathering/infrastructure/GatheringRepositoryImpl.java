package com.triplem.momoim.core.domain.gathering.infrastructure;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.triplem.momoim.core.domain.gathering.infrastructure.QGatheringEntity.gatheringEntity;
import static com.triplem.momoim.core.domain.member.infrastructure.QGatheringMemberEntity.gatheringMemberEntity;
import static com.triplem.momoim.core.domain.user.QUserEntity.userEntity;
import static com.triplem.momoim.core.domain.wishlist.infrastructure.QWishlistEntity.wishlistEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.dto.GatheringContent;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.enums.GatheringType;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.dto.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.infrastructure.QGatheringMemberEntity;
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
    private final QGatheringMemberEntity members = new QGatheringMemberEntity("members");

    @Override
    public Gathering save(Gathering gathering) {
        return gatheringJpaRepository.save(GatheringEntity.from(gathering))
            .toModel();
    }

    @Override
    public Gathering findById(Long id) {
        return gatheringJpaRepository.findById(id)
            .filter(gatheringEntity ->
                !gatheringEntity.getStatus().equals(GatheringStatus.CANCELED) && !gatheringEntity.getStatus().equals(GatheringStatus.FINISHED)
            )
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_GATHERING))
            .toModel();
    }

    @Override
    public GatheringContent getGatheringContent(Long gatheringId) {
        GatheringContent gatheringDetail = jpaQueryFactory.select(
                Projections.constructor(
                    GatheringContent.class,
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
                    gatheringEntity.wishlistCount,
                    gatheringEntity.isPeriodic,
                    gatheringEntity.nextGatheringAt
                )
            )
            .from(gatheringEntity)
            .where(
                gatheringEntity.id.eq(gatheringId),
                gatheringEntity.status.eq(GatheringStatus.OPEN).or(gatheringEntity.status.eq(GatheringStatus.CLOSED)))
            .leftJoin(userEntity).on(userEntity.id.eq(gatheringEntity.managerId))
            .fetchFirst();

        if (gatheringDetail == null) {
            throw new BusinessException(ExceptionCode.NOT_FOUND_GATHERING);
        }

        return gatheringDetail;
    }

    @Override
    public List<GatheringPreview> searchGatherings(Long userId, GatheringSearchOption searchOption) {
        return jpaQueryFactory.select(
                gatheringEntity,
                members,
                wishlistEntity
            )
            .from(gatheringEntity)
            .where(
                whereGatheringSearchOption(searchOption),
                gatheringEntity.status.ne(GatheringStatus.FINISHED),
                gatheringEntity.status.ne(GatheringStatus.CANCELED)
            )
            .leftJoin(members).on(members.gatheringId.eq(gatheringEntity.id))
            .leftJoin(userEntity).on(userEntity.id.eq(members.userId))
            .leftJoin(wishlistEntity).on(wishlistEntity.gatheringId.eq(gatheringEntity.id), wishlistEntity.userId.eq(userId))
            .limit(searchOption.getPaginationInformation().getLimit())
            .offset(searchOption.getPaginationInformation().getOffset())
            .orderBy(sortGatheringSearch(searchOption.getSortType(), searchOption.getSortOrder()), gatheringEntity.id.desc())
            .transform(gatheringPreviewParser());
    }

    @Override
    public List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(
                gatheringEntity,
                gatheringMemberEntity,
                wishlistEntity
            )
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.userId.eq(userId))
            .leftJoin(gatheringEntity).on(gatheringEntity.id.eq(gatheringMemberEntity.gatheringId))
            .leftJoin(members).on(members.gatheringId.eq(gatheringEntity.id))
            .leftJoin(wishlistEntity).on(wishlistEntity.gatheringId.eq(gatheringEntity.id), wishlistEntity.userId.eq(userId))
            .leftJoin(userEntity).on(userEntity.id.eq(members.userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(gatheringEntity.id.desc())
            .transform(gatheringPreviewParser());
    }

    @Override
    public List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(
                gatheringEntity,
                members,
                wishlistEntity
            )
            .from(gatheringEntity)
            .where(
                gatheringEntity.managerId.eq(userId)
            )
            .leftJoin(members).on(members.gatheringId.eq(gatheringEntity.id))
            .leftJoin(userEntity).on(userEntity.id.eq(members.userId))
            .leftJoin(wishlistEntity).on(wishlistEntity.gatheringId.eq(gatheringEntity.id), wishlistEntity.userId.eq(userId))
            .limit(paginationInformation.getLimit())
            .offset(paginationInformation.getOffset())
            .orderBy(gatheringEntity.id.desc())
            .transform(gatheringPreviewParser());
    }

    @Override
    public List<GatheringPreview> getGatheringPreviewsById(Long userId, List<Long> ids) {
        return jpaQueryFactory.select(
                gatheringEntity,
                members,
                wishlistEntity
            )
            .from(gatheringEntity)
            .where(
                gatheringEntity.id.in(ids)
            )
            .leftJoin(members).on(members.gatheringId.eq(gatheringEntity.id))
            .leftJoin(userEntity).on(userEntity.id.eq(members.userId))
            .leftJoin(wishlistEntity).on(wishlistEntity.gatheringId.eq(gatheringEntity.id), wishlistEntity.userId.eq(userId))
            .orderBy(gatheringEntity.id.desc())
            .transform(gatheringPreviewParser());
    }

    private BooleanBuilder whereGatheringSearchOption(GatheringSearchOption searchOption) {
        BooleanBuilder builder = new BooleanBuilder();

        if (searchOption.getGatheringIds() != null && !searchOption.getGatheringIds().isEmpty()) {
            builder.and(gatheringEntity.id.in(searchOption.getGatheringIds()));
        }

        if (searchOption.getIsOffline()) {
            builder.and(gatheringEntity.gatheringType.eq(GatheringType.OFFLINE));
        }

        if (!searchOption.getIsOffline()) {
            builder.and(gatheringEntity.gatheringType.ne(GatheringType.OFFLINE));
        }

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

    private ResultTransformer<List<GatheringPreview>> gatheringPreviewParser() {
        return groupBy(gatheringEntity.id)
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
                    wishlistEntity.isNotNull(),
                    gatheringEntity.isPeriodic,
                    list(
                        Projections.constructor(
                            GatheringMemberDetail.class,
                            members.id,
                            members.userId,
                            userEntity.email,
                            userEntity.name,
                            userEntity.profileImage,
                            members.joinedAt
                        ).skipNulls()
                    )
                )
            );
    }
}
